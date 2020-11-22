package com.ivan.ServerLeshan.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.ServerLeshan.bean.AclEntry;
import com.ivan.ServerLeshan.wrappers.ClientStore;

@Service
public class AclService {
	static Logger log = LoggerFactory.getLogger(AclService.class);

	String url = ""; //TO COMPLETE USING INFURA
	String privateKey = ""; //TO COMPLETE
	String contractAddress = ""; //Ropsten

	BigInteger gasPrice = new BigInteger("40000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	//AclStore contract; 

	List<AclEntry> ACL;

	public AclService() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			//contract = loadContract(web3j, credentials, contractAddress, gasProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ACL = new ArrayList<AclEntry>();
		ACL.add(new AclEntry("usuario1", "clientEndpoint1", 3303, Arrays.asList(5700), 1));
		ACL.add(new AclEntry("admin", "c1", 3303, Arrays.asList(5700), 1));
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

	//actions: 001-read,010-write,100-exec
	public boolean isValid(String user, String endpoint, int objectId, int resourceId, int action) {
		boolean valid = false;
		//get ACL from smartContract

		for (AclEntry entry : ACL) {
			if(user.equals(entry.getUser()) && endpoint.equals(entry.getEndpoint()) && objectId==entry.getObjectId() && (entry.getResourceId().isEmpty() || entry.getResourceId().contains(resourceId)) && isActionValid(entry.getPermission(), action)) {
				valid = true;
			}
		}

		return valid;
	}

	private boolean isActionValid(Integer acl, Integer in) {
		boolean valid = false;

		String aclString = acl.toString();
		for(int i = aclString.length(); i<3; i++) {
			aclString = "0".concat(aclString);
		}
		char[] aclDigits = aclString.toCharArray();

		String inString = in.toString();
		for(int i = inString.length(); i<3; i++) {
			inString = "0".concat(inString);
		}
		int pos = inString.indexOf("1");

		if(aclDigits[pos] == '1') {
			valid = true;
		}

		return valid;
	}
}