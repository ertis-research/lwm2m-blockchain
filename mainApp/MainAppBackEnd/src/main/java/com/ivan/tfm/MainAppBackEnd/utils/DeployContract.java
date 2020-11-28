package com.ivan.tfm.MainAppBackEnd.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.tfm.MainAppBackEnd.beans.User;
import com.ivan.tfm.MainAppBackEnd.wrappers.AclStore;
import com.ivan.tfm.MainAppBackEnd.wrappers.AnomalyStore;
import com.ivan.tfm.MainAppBackEnd.wrappers.ClientStore;
import com.ivan.tfm.MainAppBackEnd.wrappers.UserStore;

public class DeployContract {

	static Logger log = LoggerFactory.getLogger(DeployContract.class);

	public static void main(String[] args) {

		System.out.println("BEGIN deployContract");

		String url = ""; //TO COMPLETE
		String privateKey = ""; //TO COMPLETE

		BigInteger gasPrice = new BigInteger("40000000000");
		BigInteger gasLimit = new BigInteger("4712388");
		ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
		Web3j web3j;
		ClientStore clientContract;
		UserStore userContract;
		AnomalyStore anomalyContract;
		AclStore aclContract;

		try {
			web3j = Web3j.build(new HttpService(url)); // Connect to Ethereum node
			Credentials credentials = Credentials.create(privateKey); // Create credentials
			
			//White list
			List<String> addresses = new ArrayList<String>(); //Owner is included always, so you can add other addresses
			//addresses.add("");
			//addresses.add("");
			
			//Deploy smart contract
//			clientContract = ClientStore.deploy(web3j, credentials, gasProvider, addresses).send(); 
//			userContract = UserStore.deploy(web3j, credentials, gasProvider, addresses).send();
//			anomalyContract = AnomalyStore.deploy(web3j, credentials, gasProvider, addresses).send();
//			aclContract = AclStore.deploy(web3j, credentials, gasProvider, addresses).send();

//			System.out.println("ClientContract Address: " + clientContract.getContractAddress());
//			System.out.println("UserContract Address: " + userContract.getContractAddress());
//			System.out.println("AnomalyContract Address: " + anomalyContract.getContractAddress());
//			System.out.println("AclContract Address: " + aclContract.getContractAddress());

			//Add Admin user
//			addAdmin(web3j, credentials, gasProvider);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("END deployContract");
	}
	
	private static void addAdmin(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws Exception {
		System.out.println("--> BEGIN addUser");
		String contractAddress = ""; //Ropsten
		UserStore contract = UserStore.load(contractAddress, web3j, credentials, gasProvider);
		User u = new User("admin", "admin1@test.com", "admin", 1);
		byte[] username = Converter.asciiToByte32(u.getUsername());
		byte[] email = Converter.asciiToByte32(u.getEmail());
		byte[] password = Converter.asciiToByte32(u.getPassword());
		BigInteger role = BigInteger.valueOf(u.getRole());

		int existUser = contract.existsUser(username).send().intValue();

		if(existUser == -1) {
			TransactionReceipt txReceipt = contract.addUser(username, email, password, role).send();
			System.out.println("TransactionHash: " + txReceipt.getTransactionHash());
		}
		System.out.println("\n--> END addUser");
	}
}




