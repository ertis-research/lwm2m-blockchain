import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.leshan.core.request.Identity;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapSession;
import org.eclipse.leshan.server.security.SecurityInfo;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import utils.ClientUtil;
import utils.Converter;
import wrappers.ClientStore;

public class ClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(ClientTest.class);

	private static void testAddClientsJSONFile(int num) {
		System.out.println("--> BEGIN Test addClient in JSONFile");
		JSONFile jFile = new JSONFile();
		jFile.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			BootstrapConfig bsConfig = ClientUtil.createBoostrapConfig("coaps://localhost:5684", "clientbs"+i, "654321", "coaps://localhost:5784", "clients"+i, "123456");
			jFile.addClient("clientEndpoint"+i,bsConfig);

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test addClient in JSONFile");
	}

	private static void testRemoveClientsJSONFile(int num) {
		System.out.println("--> BEGIN Test removeClient in JSONFile");
		JSONFile jFile = new JSONFile();
		jFile.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			jFile.removeClient("clientEndpoint"+i);

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test removeClient in JSONFile");
	}

	private static void testGetClientsJSONFile(int num) {
		System.out.println("--> BEGIN Test getClient in JSONFile");
		JSONFile jFile = new JSONFile();
		jFile.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			BootstrapConfig config = jFile.getClient("clientEndpoint1", null, null);

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getClient in JSONFile");
	}

	private static void testGetAllClientsJSONFile(int num) {
		System.out.println("--> BEGIN Test getAllClients in JSONFile");
		JSONFile jFile = new JSONFile();
		jFile.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			Map<String, BootstrapConfig> clients = jFile.getAllClients();

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getAllClients in JSONFile");
	}

	//to generate security.data file
	private static void addClientsToLwM2MServer(int num) {
		String filename = "data/security.data";
		Collection<SecurityInfo> alls = new ArrayList<SecurityInfo>();
		
		//create security infos
		for (int i = 0; i < num; i++) {	
			SecurityInfo securityInfo = SecurityInfo.newPreSharedKeyInfo("clientEndpoint"+i, "clients"+i,Converter.hexToByte("123456"));
			alls.add(securityInfo);
		}
		
		//save in file
		try {
            File file = new File(filename);
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));) {
                out.writeObject(alls.toArray(new SecurityInfo[0]));
            }
        } catch (IOException e) {
            LOG.error("Could not save security infos to file", e);
        }
	}
	
	//Test blockchain
	private static void testAddClientsBlockchain(int num) {
		System.out.println("--> BEGIN Test addClient in Blockchain");
		Blockchain bc = new Blockchain();
		bc.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			bc.addClient("clientEndpoint"+i,"coaps://localhost:5684", "clientbs"+i, "654321", "coaps://localhost:5784", "clients"+i, "123456");

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test addClient in Blockchain");
	}

	private static void testRemoveClientsBlockchain(int num) {
		System.out.println("--> BEGIN Test removeClient in Blockchain");
		Blockchain bc = new Blockchain();
		bc.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			bc.removeClient("clientEndpoint"+i);

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test removeClient in Blockchain");
	}

	private static void testGetClientsBlockchain(int num) {
		System.out.println("--> BEGIN Test getClient in Blockchain");
		Blockchain bc = new Blockchain();
		bc.getAllClients(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			BootstrapConfig config = bc.getClient("clientEndpoint1", null, null);

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getClient in Blockchain");
	}

	private static void testGetAllClientsBlockchain(int num) {
		System.out.println("--> BEGIN Test getAllClients in Blockchain");
		Blockchain bc = new Blockchain();
		System.out.println(bc.getAllClients().size()); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			Map<String, BootstrapConfig> clients = bc.getAllClients();

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getAllClients in Blockchain");
	}


	public static void main(String[] args) {

		//TEST - JSON file
		//testAddClientsJSONFile(5);
		//testRemoveClientsJSONFile(5);
		//testGetClientsJSONFile(5);
		//testGetAllClientsJSONFile(5);
		//addClientsToLwM2MServer(500);

		//TEST - Blockchain
		//testAddClientsBlockchain(5);
		//testRemoveClientsBlockchain(5);
		//testGetClientsBlockchain(5);
		//testGetAllClientsBlockchain(5);
		System.out.println("END");

	}

	public static class JSONFile{
		private String filename;
		private Gson gson;
		private Type gsonType;

		public JSONFile() {
			filename = "data/bootstrap.json";
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();
			this.gson = builder.create();
			this.gsonType = new TypeToken<Map<String, BootstrapConfig>>() {
			}.getType();
		}

		public void addClient(String endpoint, BootstrapConfig config) {
			Map<String, BootstrapConfig> securityInfoClients = getAllClients();
			securityInfoClients.put(endpoint, config);
			saveToFile(securityInfoClients);
		}

		public BootstrapConfig getClient(String endpoint, Identity deviceIdentity, BootstrapSession session) {
			return getAllClients().get(endpoint);
		}

		public Map<String, BootstrapConfig> getAllClients() {
			Map<String, BootstrapConfig> res = new ConcurrentHashMap<>();
			try {
				File file = new File(filename);
				if (file.exists()) {
					try (InputStreamReader in = new InputStreamReader(new FileInputStream(file))) {
						Map<String, BootstrapConfig> configs = gson.fromJson(in, gsonType);
						for (Map.Entry<String, BootstrapConfig> config : configs.entrySet()) {
							res.put(config.getKey(), config.getValue());
							//System.out.println(config.getKey());
						}
					}
				}
			} catch (Exception e) {
				LOG.error("Could not load bootstrap infos from file", e);
			} 
			return res;
		}

		public BootstrapConfig removeClient(String enpoint) {
			Map<String, BootstrapConfig> securityInfoClients = getAllClients();
			BootstrapConfig bootstrapConfig = securityInfoClients.remove(enpoint);
			saveToFile(securityInfoClients);	
			return bootstrapConfig;
		}

		private void saveToFile(Map<String, BootstrapConfig> securityInfoClients) {
			try {
				// Create file if it does not exists.
				File file = new File(filename);
				if (!file.exists()) {
					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}
					file.createNewFile();
				}

				// Write file
				try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename))) {
					out.write(gson.toJson(securityInfoClients, gsonType));
				}
			} catch (Exception e) {
				LOG.error("Could not save bootstrap infos to file", e);
			}
		}
	}

	public static class Blockchain{

		private ClientStore contract;

		public Blockchain() {
			String url = ""; //TO COMPLETE USING INFURA
			String privateKey = ""; //TO COMPLETE
			String contractAddress = ""; //TO COMPLETE

			BigInteger gasPrice = new BigInteger("40000000000");
			BigInteger gasLimit = new BigInteger("4712388");
			ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

			try {
				Web3j web3j = Web3j.build(new HttpService(url));
				Credentials credentials = Credentials.create(privateKey);
				contract = ClientStore.load(contractAddress, web3j, credentials, gasProvider);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void addClient(String endpoint,
				String url_bs, String id_bs, String key_bs,
				String url_s, String id_s, String key_s) {

			byte[] endpointByte = Converter.asciiToByte32(endpoint);
			byte[] url_bsByte = Converter.asciiToByte32(url_bs);
			byte[] id_bsByte = Converter.asciiToByte32(id_bs);
			byte[] key_bsByte = Converter.asciiToByte32(key_bs);
			byte[] url_sByte = Converter.asciiToByte32(url_s);
			byte[] id_sByte = Converter.asciiToByte32(id_s);
			byte[] key_sByyte = Converter.asciiToByte32(key_s);

			TransactionReceipt txReceipt;
			try {
				txReceipt = contract.addClient(endpointByte,
						url_bsByte, id_bsByte, key_bsByte,
						url_sByte, id_sByte, key_sByyte).send();
				System.out.println("txReceipt addClient: "+txReceipt.getTransactionHash());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public BootstrapConfig getClient(String endpoint, Identity deviceIdentity, BootstrapSession session) {
			BootstrapConfig bootstrapConfig = null;

			try {
				Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> response = contract.getClient(Converter.asciiToByte32(endpoint)).send();
				bootstrapConfig = ClientUtil.createBoostrapConfig(Converter.byteToAscii(response.component1()), Converter.byteToAscii(response.component2()), Converter.byteToAscii(response.component3()), Converter.byteToAscii(response.component4()), Converter.byteToAscii(response.component5()), Converter.byteToAscii(response.component6()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bootstrapConfig;
		}

		public void removeClient(String endpoint) {
			try {
				TransactionReceipt txReceipt = contract.removeClient(Converter.asciiToByte32(endpoint)).send();
				System.out.println("txReceipt removeClient: "+txReceipt.getTransactionHash());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Map<String, BootstrapConfig> getAllClients() {
			Map<String, BootstrapConfig> res = new ConcurrentHashMap<>();

			try {
				res = getAllFromBlockchain();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}

		public Map<String, BootstrapConfig> getAllFromBlockchain(){
			Map<String, BootstrapConfig> res = new ConcurrentHashMap<>();

			try {
				Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> response = contract.getAllClients().send();
				res = ClientUtil.clientBytesToMap(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}
	}

}
