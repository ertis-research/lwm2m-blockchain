import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import utils.Converter;
import wrappers.UserStore;

public class UserTest {
	
	private static void testAddUsers(int num) {
		System.out.println("--> BEGIN Test addUser");
		UserBlockchain bc = new UserBlockchain();
		bc.getAll(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();
			User u = new User("user"+i, "user"+i+"@test.com", "password", 1);
			bc.addUser(u);
			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test addUser");
	}
	
	private static void testGetAll(int num) {
		System.out.println("--> BEGIN Test getAll");
		UserBlockchain bc = new UserBlockchain();
		System.out.println(bc.getAll().size()); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();
			List<User> users = bc.getAll();
			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms - " + users.get(6).username);
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test getAll");
	}
	
	private static void testUpdateUsers(int num) {
		System.out.println("--> BEGIN Test UpdateUser");
		UserBlockchain bc = new UserBlockchain();
		bc.getAll(); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();
			User u = new User("user"+i, "user"+i+"@test.com", "password", 2);
			bc.updateUser(u);
			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms");
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test UpdateUser");
	}
	
	private static void testValidateLogin(int num) {
		System.out.println("--> BEGIN Test validateLogin");
		UserBlockchain bc = new UserBlockchain();
		System.out.println(bc.getAll().size()); 
		long totalTime = 0;
		for (int i = 0; i < num; i++) {
			long startTime = System.currentTimeMillis();
			Credentials c = bc.validateLogin("user2", "password");
			long endTime = System.currentTimeMillis();
			long iterationTime = ((endTime - startTime));
			totalTime += iterationTime;
			System.out.println("\titeration "+(i+1)+": " +iterationTime + " ms "+c.email);
		}
		System.out.println("Total time " + num + " iterations: " + totalTime+ " ms");
		System.out.println("Average time each iteration: "+ totalTime/num+ " ms");
		System.out.println("\n--> END Test validateLogin");
	}
	
	public static void main(String[] args) {
//		testAddUsers(10);
//		testGetAll(100);
//		testUpdateUsers(10);
//		testValidateLogin(100);

	}
	
	public static class UserBlockchain{

		private UserStore contract;

		public UserBlockchain() {
			String url = ""; //TO COMPLETE USING INFURA
			String privateKey = ""; //TO COMPLETE
			String contractAddress = ""; //TO COMPLETE

			BigInteger gasPrice = new BigInteger("20000000000");
			BigInteger gasLimit = new BigInteger("4712388");
			ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

			try {
				Web3j web3j = Web3j.build(new HttpService(url));
				org.web3j.crypto.Credentials credentials = org.web3j.crypto.Credentials.create(privateKey);
				contract = UserStore.load(contractAddress, web3j, credentials, gasProvider);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public List<User> getAll(){
			List<User> users = new ArrayList<User>();
			
			try {
				Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>> response = contract.getAllUsers().send();
				users = this.tuple4ToList(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return users;
		}
		
		public void addUser(User u) {
			try {
				if(this.existsUser(u.getUsername()) == -1) {
					byte[] username = Converter.asciiToByte32(u.getUsername());
					byte[] email = Converter.asciiToByte32(u.getEmail());
					byte[] password = Converter.asciiToByte32(u.getPassword());
					BigInteger role = BigInteger.valueOf(u.getRole());
					
					TransactionReceipt txReceipt = contract.addUser(username, email, password, role).send();
//					this.blockchainService.logTransaction(txReceipt, "addUser", this.contractName);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void updateUser(User u) {
			try {
				if(this.existsUser(u.getUsername()) != -1) {
					byte[] username = Converter.asciiToByte32(u.getUsername());
					byte[] email = Converter.asciiToByte32(u.getEmail());
					byte[] password = Converter.asciiToByte32(u.getPassword());
					BigInteger role = BigInteger.valueOf(u.getRole());
					
					TransactionReceipt txReceipt = contract.updateUser(username, email, password, role).send();
//					this.blockchainService.logTransaction(txReceipt, "updateUser", this.contractName);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public Credentials validateLogin(String wildcard, String password) {
			Credentials c = new Credentials("", "", 0, "");
			try {
				byte[] wildcard_bytes = Converter.asciiToByte32(wildcard);
				byte[] password_bytes = Converter.asciiToByte32(password);
				Tuple3<byte[], byte[], BigInteger> response = contract.validateLogin(wildcard_bytes, password_bytes).send();
				c = this.tuple3ToList(response);
				return c;
			} catch(Exception e) {
				e.printStackTrace();
			}
			return c;
		}
		
		
		//PRIVATE FUNCTIONS
		private List<User> tuple4ToList(Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>> tuple) {
			List<User> users = new ArrayList<User>();
			List<byte[]> usernames = tuple.component1();
			List<byte[]> emails = tuple.component2();
			List<byte[]> passwords = tuple.component3();
			List<BigInteger> roles = tuple.component4();
			
			for (int i=0; i<tuple.component1().size(); i++) {
				String username = Converter.byteToAscii(usernames.get(i));
				String email = Converter.byteToAscii(emails.get(i));
				String password = Converter.byteToAscii(passwords.get(i));
				int role = roles.get(i).intValue();
				User user = new User(username, email, password, role);
				users.add(user);
			}
			return users;
		}
		
		private int existsUser(String username) throws Exception{
			BigInteger exists = contract.existsUser(Converter.asciiToByte32(username)).send();
			return exists.intValue();
		}

		private Credentials tuple3ToList(Tuple3<byte[], byte[], BigInteger> tuple) {
			String username = Converter.byteToAscii(tuple.component1());
			String email = Converter.byteToAscii(tuple.component2());
			int role = tuple.component3().intValue();
			
			Credentials c = new Credentials(username, email, role, "");
			return c;
		}
	}
	
	public static class User {

		private String username;
		private String email;
		private String password;
		private Integer role;
		
		public User(String username, String email, String password, Integer role) {
			this.username = username;
			this.email = email;
			this.password = password;
			this.role = role;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Integer getRole() {
			return role;
		}

		public void setRole(Integer role) {
			this.role = role;
		}
		
	}
	
	public static class Credentials {
		
		private String username;
		private String email;
		private int role;
		private String token;
		
		public Credentials(String username, String email, int role, String token) {
			this.username = username;
			this.email = email;
			this.role = role;
			this.token = token;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getRole() {
			return role;
		}
		public void setRole(int role) {
			this.role = role;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		
	}
}
