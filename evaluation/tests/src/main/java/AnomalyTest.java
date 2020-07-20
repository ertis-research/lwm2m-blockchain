import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import utils.Converter;
import wrappers.AnomalyStore;

public class AnomalyTest {

	private static void testAddAnomalies(int num) {
		System.out.println("--> BEGIN Test addAnomaly");
		AnomalyBlockchain bc = new AnomalyBlockchain();
		bc.getAll(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();
			Anomaly a = new Anomaly(System.currentTimeMillis(), "clientEndpoint1", 3, 52.68);
			bc.addAnomaly(a);
			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test addAnomaly");
	}
	
	private static void testGetAll(int num) {
		System.out.println("--> BEGIN Test getAllAnomalies");
		AnomalyBlockchain bc = new AnomalyBlockchain();
		System.out.println(bc.getAll().size()); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();

			List<Anomaly> anomalies = bc.getAll();

			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getAllAnomalies");
	}
	
	public static void main(String[] args) {
//		testAddAnomalies(10);
//		testGetAll(100);

	}

	public static class AnomalyBlockchain{

		private AnomalyStore contract;

		public AnomalyBlockchain() {
			String url = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32"; //Ropsten
			String privateKey = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8"; //Private key of an account, Ropsten
			String contractAddress = "0x136f287df23e92968e930c0eee416e3bd87648b7"; //Ropsten

			BigInteger gasPrice = new BigInteger("20000000000");
			BigInteger gasLimit = new BigInteger("4712388");
			ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

			try {
				Web3j web3j = Web3j.build(new HttpService(url));
				Credentials credentials = Credentials.create(privateKey);
				contract = AnomalyStore.load(contractAddress, web3j, credentials, gasProvider);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public List<Anomaly> getAll(){
			List<Anomaly> anomalies = new ArrayList<Anomaly>();

			try {
				Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>> response = contract.getAllAnomalies().send();
				anomalies = tuple4ToList(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return anomalies;
		}

		public void addAnomaly(Anomaly a){
			BigInteger ts  = BigInteger.valueOf(a.getTimestamp());
			byte[] endpoint = Converter.asciiToByte32(a.getEndpoint());
			BigInteger emergency = BigInteger.valueOf(a.getEmergencyLevel());
			BigInteger temp = BigInteger.valueOf(Math.round(100 * a.getTemperature()));

			try {
				TransactionReceipt txReceipt = contract.addAnomaly(ts, endpoint, emergency, temp).send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//PRIVATE FUNCTIONS
		private List<Anomaly> tuple4ToList(Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>> tuple){
			List<Anomaly> anomalies = new ArrayList<Anomaly>();

			List<BigInteger> timestamps = tuple.component1();
			List<byte[]> endpoints = tuple.component2();
			List<BigInteger> emergencyLevels = tuple.component3();
			List<BigInteger> temperatures = tuple.component4();

			for (int i = 0; i < tuple.component1().size(); i++) {
				long timestamp = timestamps.get(i).longValue();
				String endpoint = Converter.byteToAscii(endpoints.get(i));
				int emergencyLevel = emergencyLevels.get(i).intValue();
				double temperature = (temperatures.get(i).doubleValue())/100;
				Anomaly anomaly = new Anomaly(timestamp, endpoint, emergencyLevel, temperature);
				anomalies.add(anomaly);
			}
			return anomalies;
		}
	}

	public static class Anomaly {

		private Long timestamp;
		private String endpoint;
		private int emergencyLevel;
		private double temperature;

		public Anomaly(Long timestamp, String endpoint, int emergencyLevel, double temperature) {
			this.timestamp = timestamp;
			this.endpoint = endpoint;
			this.emergencyLevel = emergencyLevel;
			this.temperature = temperature;
		}

		public Long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}

		public String getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

		public int getEmergencyLevel() {
			return emergencyLevel;
		}

		public void setEmergencyLevel(int emergencyLevel) {
			this.emergencyLevel = emergencyLevel;
		}

		public double getTemperature() {
			return temperature;
		}

		public void setTemperature(double temperature) {
			this.temperature = temperature;
		}

		@Override
		public String toString() {
			return "Anomaly [timestamp=" + timestamp + ", endpoint=" + endpoint + ", emergencyLevel=" + emergencyLevel
					+ ", temperature=" + temperature + "]";
		}

	}


}
