package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.tfm.MainAppBackEnd.beans.BootstrapConfig;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;
import com.ivan.tfm.MainAppBackEnd.wrappers.BootstrapStore;


public class BlockchainService {

	static Logger log = LoggerFactory.getLogger(BlockchainService.class);

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

	public BlockchainService() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			contract = loadContract(web3j, credentials, contractAddress, gasProvider);
		} catch (Exception e) {
			e.printStackTrace();
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


	private int existsClient(BootstrapStore contract, String endpoint) throws Exception{
		long startTime = System.currentTimeMillis();
		BigInteger exist = contract.existsClient(Converter.asciiToByte32(endpoint)).send();
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("existClient: "+ totalTime + " ms");
		return exist.intValue();
	}

	public void addClient(String endpoint,
			String url_bs, String id_bs, String key_bs,
			String url_s, String id_s, String key_s) throws Exception{

		long startTime = System.currentTimeMillis();
		if(existsClient(contract, endpoint) == -1) {
			byte[] endpointByte = Converter.asciiToByte32(endpoint);
			byte[] url_bsByte = Converter.asciiToByte32(url_bs);
			byte[] id_bsByte = Converter.asciiToByte32(id_bs);
			byte[] key_bsByte = Converter.asciiToByte32(key_bs);
			byte[] url_sByte = Converter.asciiToByte32(url_s);
			byte[] id_sByte = Converter.asciiToByte32(id_s);
			byte[] key_sByyte = Converter.asciiToByte32(key_s);

			TransactionReceipt txReceipt = contract.addClient(endpointByte,
					url_bsByte, id_bsByte, key_bsByte,
					url_sByte, id_sByte, key_sByyte).send();
			logTransaction(txReceipt, "addClient");
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("addClient: "+ totalTime + " ms");
	}

	public void removeClient(String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		
		try {
			if(existsClient(contract, endpoint) != -1) {
				TransactionReceipt txReceipt = contract.removeClient(Converter.asciiToByte32(endpoint)).send();
				logTransaction(txReceipt, "removeClient");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("removeClient: "+ totalTime + " ms");
	}

	public String[] getAllEndpoints() {
		long startTime = System.currentTimeMillis();
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			list = contract.getAllClients().send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> list2 = new ArrayList<String>();
		for(byte[] aux : list) {
			list2.add(Converter.byteToAscii(aux));
		}
		String[] response = new String[list.size()];
		list2.toArray(response);
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getAllEndpoints: "+ totalTime + " ms");
		return response;
	}

	private BootstrapConfig getClient(BootstrapStore contract, String endpoint) throws Exception {
		//long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> response = contract.getClient(Converter.asciiToByte32(endpoint)).send();
		BootstrapConfig bootstrapConfig = new BootstrapConfig(endpoint, response);
		//long endTime = System.currentTimeMillis();
		//long totalTime = ((endTime - startTime));
		//System.out.println("getClient: "+ totalTime + " ms");
		return bootstrapConfig;
	}

	public List<BootstrapConfig> getAllClients(){
		long startTime = System.currentTimeMillis();
		List<BootstrapConfig> res = new ArrayList<BootstrapConfig>();
		
		try {
			String[] endpoints = getAllEndpoints();
			for (String end : endpoints) {
				BootstrapConfig config = getClient(contract, end);
				res.add(config);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getAllClients: "+ totalTime + " ms");
		return res;
	}

}
