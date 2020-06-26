import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.leshan.SecurityMode;
import org.eclipse.leshan.core.request.Identity;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerSecurity;
import org.eclipse.leshan.server.bootstrap.BootstrapSession;
import org.eclipse.leshan.server.bootstrap.EditableBootstrapConfigStore;
import org.eclipse.leshan.server.bootstrap.InvalidConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;


public class BlockchainBootstrapStore implements EditableBootstrapConfigStore {
	static Logger log = LoggerFactory.getLogger(BlockchainBootstrapStore.class);

	String url = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32"; //Ropsten
	String privateKey = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8"; //Private key of an account, Ropsten
	String contractAddress = "0x0b65c81b465953fd25b29c0caffd2a448f0b948f"; //Ropsten
	
	//String url = "HTTP://127.0.0.1:7545"; //Ganache
	//String privateKey = "a701158005907d33a130caa07a2b7d811b409336ba14efca9a00b8593ec6feb9"; //Private key of an account, Ganache
	//String contractAddress = "0x17EC1b6b63b5B477Aa38E5389e98765573Db5415"; //Ganache
	
	BigInteger gasPrice = new BigInteger("20000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	BootstrapStore contract;

	public BlockchainBootstrapStore() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			contract = loadContract(web3j, credentials, contractAddress, gasProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Get all clients security information
	@Override
	public Map<String, BootstrapConfig> getAll() {
		//System.out.println("BlockchainBootstrapStore - getAll");
		Map<String, BootstrapConfig> res = new ConcurrentHashMap<>();

		try {
			String[] endpoints = getAllClients(contract);
			System.out.println(endpoints);
			for (String end : endpoints) {
				BootstrapConfig config = getClient(contract, end);
				res.put(end, config);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	//Add a new client
	@Override
	public void add(String endpoint, BootstrapConfig config) throws InvalidConfigurationException {
		System.out.println("BlockchainBootstrapStore - add (Nothing to do)");
	}

	//Delete a client
	@Override
	public BootstrapConfig remove(String endpoint) {
		System.out.println("BlockchainBootstrapStore - remove (Nothing to do)");
		return null;
	}

	//Get a client's security information from an endpoint 
	@Override
	public BootstrapConfig get(String endpoint, Identity deviceIdentity, BootstrapSession session) {
		//System.out.println("BlockchainBootstrapStore - get");
		BootstrapConfig bootstrapConfig = null;

		try {
			bootstrapConfig = getClient(contract, endpoint);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bootstrapConfig;
	}

	public class BootstrapConfigWeb3{
		String url_bs;
		String id_bs;
		String key_bs;
		String url_s;
		String id_s;
		String key_s;

		public BootstrapConfigWeb3(byte[] url_bs, byte[] id_bs, byte[] key_bs,byte[]  url_s, byte[] id_s, byte[] key_s) {
			this.url_bs = byteToAscii(url_bs);
			this.id_bs = byteToAscii(id_bs);
			this.key_bs = byteToAscii(key_bs);
			this.url_s = byteToAscii(url_s);
			this.id_s = byteToAscii(id_s);
			this.key_s = byteToAscii(key_s);
		}

		public BootstrapConfigWeb3(Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]> clientConfig) {
			this.url_bs = byteToAscii(clientConfig.component1());
			this.id_bs = byteToAscii(clientConfig.component2());
			this.key_bs = byteToAscii(clientConfig.component3());
			this.url_s = byteToAscii(clientConfig.component4());
			this.id_s = byteToAscii(clientConfig.component5());
			this.key_s = byteToAscii(clientConfig.component6());
		}

		@Override
		public String toString() {
			return "BootstrapConfig [url_bs=" + url_bs + ", id_bs=" + id_bs + ", key_bs=" + key_bs + ", url_s=" + url_s
					+ ", id_s=" + id_s + ", key_s=" + key_s + "]";
		}
	}


	// Connect to Ethereum node
	private Web3j connect(String url) throws Exception {
		Web3j web3j = Web3j.build(new HttpService(url));
		log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
		return web3j;
	}

	// Create credentials
	private Credentials createCredentials(String privateKey) throws Exception {
		Credentials credentials = Credentials.create(privateKey);
		log.info("Credentials created");
		return credentials;
	}

	// Load smart contract
	private BootstrapStore loadContract(Web3j web3j, Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		BootstrapStore contract = BootstrapStore.load(address, web3j, credentials, provider);
		log.info("Smart contract loaded");
		return contract;
	}

	private void logTransaction(TransactionReceipt txReceipt, String txName) {
		String txHash = txReceipt.getTransactionHash();
		BigInteger blockNumber = txReceipt.getBlockNumber();
		String blockHash = txReceipt.getBlockHash();
		BigInteger gasUsed = txReceipt.getGasUsed();
		log.info("EXECUTED TRANSACTION " + txName);
		log.info("Tx Hash: " + txHash);
		log.info("Block Number: " + blockNumber);
		log.info("Block Hash: " + blockHash);
		log.info("Gas Used: " + gasUsed);
	}

	private BootstrapConfig getClient(BootstrapStore contract, String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> response = contract.getClient(asciiToByte32(endpoint)).send();
		log.info("CLIENT ENDPOINT " + endpoint + " DATA STORED");
		log.info("BS Server URL: " + byteToAscii(response.component1()));
		log.info("BS Identity: " + byteToAscii(response.component2()));
		log.info("BS Key: " + byteToAscii(response.component3()));
		log.info("Server URL: " + byteToAscii(response.component4()));
		log.info("Identity: " + byteToAscii(response.component5()));
		log.info("Key: " + byteToAscii(response.component6()));

		BootstrapConfigWeb3 bootstrapConfigWeb3 = new BootstrapConfigWeb3(response);
		BootstrapConfig bootstrapConfig = bootstrapConfigWeb3ToBootstrapConfig(bootstrapConfigWeb3);
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getClient: "+ totalTime + " ms");
		return bootstrapConfig;
	}

	private String[] getAllClients(BootstrapStore contract) throws Exception {
		long startTime = System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		List<byte[]> list = contract.getAllClients().send();
		List<String> list2 = new ArrayList<String>();
		for(byte[] aux : list) {
			list2.add(byteToAscii(aux));
		}
		String[] response = new String[list.size()];
		list2.toArray(response);
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getAllClients: "+ totalTime + " ms");
		return response;
	}

	private BootstrapConfig bootstrapConfigWeb3ToBootstrapConfig(BootstrapConfigWeb3 bootstrapConfigWeb3) {
		BootstrapConfig bootstrapConfig = new BootstrapConfig();
		bootstrapConfig.toDelete = Arrays.asList("/0","/1");
		ServerConfig serverConfig = createServerConfig();
		bootstrapConfig.servers.put(0, serverConfig);

		ServerSecurity bsSecurity =  createServerSecurity(bootstrapConfigWeb3.url_bs, true, asciiToByte(bootstrapConfigWeb3.id_bs), hexToByte(bootstrapConfigWeb3.key_bs));
		ServerSecurity serverSecurity =  createServerSecurity(bootstrapConfigWeb3.url_s, false, asciiToByte(bootstrapConfigWeb3.id_s), hexToByte(bootstrapConfigWeb3.key_s));

		bootstrapConfig.security.put(0, bsSecurity);
		bootstrapConfig.security.put(1, serverSecurity);

		return bootstrapConfig;
	}

	private ServerSecurity createServerSecurity(String uri, boolean bootstrapServer, byte[] publicKeyOrId, byte[] secretKey) {
		ServerSecurity serverSecurity = new ServerSecurity();
		serverSecurity.uri = uri;
		serverSecurity.bootstrapServer = bootstrapServer;
		serverSecurity.securityMode = SecurityMode.PSK;
		serverSecurity.serverId = bootstrapServer ? 111 : 123;
		serverSecurity.publicKeyOrId = publicKeyOrId;
		serverSecurity.secretKey = secretKey;
		return serverSecurity;
	}

	private ServerConfig createServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.shortId = 123;
		serverConfig.lifetime = 20;
		return serverConfig;
	}

	private byte[] asciiToByte32(String ascii){
		byte[] bytearray = new byte[32];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};

	private byte[] asciiToByte(String ascii){
		byte[] bytearray = new byte[ascii.length()];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};

	private String byteToAscii(byte[] bytes) {
		String res="";
		try {
			res = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return res.trim();
	}

	private byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len/2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
}