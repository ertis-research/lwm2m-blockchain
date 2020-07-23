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
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;


public class BlockchainBootstrapStore implements EditableBootstrapConfigStore {
	static Logger log = LoggerFactory.getLogger(BlockchainBootstrapStore.class);

	String url = ""; //TO COMPLETE USING INFURA
	String privateKey = ""; //TO COMPLETE
	String contractAddress = "0x6c8568e24548115439174e86d98fb8d7d3de2c4b"; //Ropsten
	
	BigInteger gasPrice = new BigInteger("20000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	ClientStore contract;

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
			List<BootstrapConfigWeb3> bootstrapConfigWeb3= getAllClients();
			
			for (BootstrapConfigWeb3 configWeb3 : bootstrapConfigWeb3) {
				BootstrapConfig config = bootstrapConfigWeb3ToBootstrapConfig(configWeb3);
				res.put(configWeb3.endpoint, config);
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
		String endpoint;
		String url_bs;
		String id_bs;
		String key_bs;
		String url_s;
		String id_s;
		String key_s;

		public BootstrapConfigWeb3(String endpoint, String url_bs, String id_bs, String key_bs, String url_s, String id_s, String key_s) {
			this.endpoint = endpoint;
			this.url_bs = url_bs;
			this.id_bs = id_bs;
			this.key_bs = key_bs;
			this.url_s = url_s;
			this.id_s = id_s;
			this.key_s = key_s;
		}

		public BootstrapConfigWeb3(String endpoint, Tuple6<byte[], byte[], byte[], byte[], byte[], byte[]> clientConfig) {
			this.endpoint = endpoint;
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
	private ClientStore loadContract(Web3j web3j, Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		ClientStore contract = ClientStore.load(address, web3j, credentials, provider);
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

	private BootstrapConfig getClient(ClientStore contract, String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> response = contract.getClient(asciiToByte32(endpoint)).send();
		log.info("CLIENT ENDPOINT " + endpoint + " DATA STORED");
		log.info("BS Server URL: " + byteToAscii(response.component1()));
		log.info("BS Identity: " + byteToAscii(response.component2()));
		log.info("BS Key: " + byteToAscii(response.component3()));
		log.info("Server URL: " + byteToAscii(response.component4()));
		log.info("Identity: " + byteToAscii(response.component5()));
		log.info("Key: " + byteToAscii(response.component6()));

		BootstrapConfigWeb3 bootstrapConfigWeb3 = new BootstrapConfigWeb3(endpoint, response);
		BootstrapConfig bootstrapConfig = bootstrapConfigWeb3ToBootstrapConfig(bootstrapConfigWeb3);
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getClient: "+ totalTime + " ms");
		return bootstrapConfig;
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

	public List<BootstrapConfigWeb3> getAllClients(){
		List<BootstrapConfigWeb3> configs = new ArrayList<BootstrapConfigWeb3>();

		try {
			Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> response = contract.getAllClients().send();
			configs = tuple7ToList(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configs;
	}

	private List<BootstrapConfigWeb3> tuple7ToList(Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> tuple){
		List<BootstrapConfigWeb3> bootstrapConfigs = new ArrayList<BootstrapConfigWeb3>();

		List<byte[]> endpoints = tuple.component1();
		List<byte[]> urls_bs = tuple.component2();
		List<byte[]> ids_bs = tuple.component3();
		List<byte[]> keys_bs = tuple.component4();
		List<byte[]> urls_s = tuple.component5();
		List<byte[]> ids_s = tuple.component6();
		List<byte[]> keys_s = tuple.component7();

		for (int i = 0; i < tuple.component1().size(); i++) {
			String endpoint = byteToAscii(endpoints.get(i));
			String url_bs = byteToAscii(urls_bs.get(i));
			String id_bs = byteToAscii(ids_bs.get(i));
			String key_bs = byteToAscii(keys_bs.get(i));
			String url_s = byteToAscii(urls_s.get(i));
			String id_s = byteToAscii(ids_s.get(i));
			String key_s = byteToAscii(keys_s.get(i));

			BootstrapConfigWeb3 bootstrapConfig = new BootstrapConfigWeb3(endpoint, url_bs, id_bs, key_bs, url_s, id_s, key_s);
			bootstrapConfigs.add(bootstrapConfig);
		}
		return bootstrapConfigs;
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