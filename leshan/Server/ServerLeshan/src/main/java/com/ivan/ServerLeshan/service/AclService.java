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
import com.ivan.ServerLeshan.utils.Converter;
import com.ivan.ServerLeshan.wrappers.AclStore;


@Service
public class AclService {
	static Logger log = LoggerFactory.getLogger(AclService.class);

	String url = ""; //TO COMPLETE USING INFURA
	String privateKey = ""; //TO COMPLETE
	String contractAddress = "0x4071f645f4fb7fa966ae30fd755f1ef647854833"; //Ropsten

	BigInteger gasPrice = new BigInteger("40000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	AclStore contract; 

	List<AclEntry> ACL;

	public AclService() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			contract = loadContract(web3j, credentials, contractAddress, gasProvider);
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
	private AclStore loadContract(Web3j web3j, Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		AclStore contract = AclStore.load(address, web3j, credentials, provider);
		log.info("Smart contract loaded");
		return contract;
	}

	public int getUserPermission(String username, String client_name, int object_id, int resource_id) {
		int permission = 0;
		
		try {
			byte[] usnm = Converter.asciiToByte32(username);
			byte[] cl_nm = Converter.asciiToByte32(client_name);
			BigInteger obj_id = BigInteger.valueOf(object_id);
			BigInteger res_id = BigInteger.valueOf(resource_id);
			BigInteger response =	contract.getUserPermission(usnm, cl_nm, obj_id, res_id).send();
			permission = response.intValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return permission;
	}
	
	public boolean isPermissionValid(int[] requiredPermission, int userPermission) {
		for (int i = 0; i < requiredPermission.length; i++) {
			if(requiredPermission[i] == userPermission) {
				return true;
			}
		}
		return false;
	}
	
}