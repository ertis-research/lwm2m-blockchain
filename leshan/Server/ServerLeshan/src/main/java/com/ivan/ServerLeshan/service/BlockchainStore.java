package com.ivan.ServerLeshan.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.eclipse.leshan.server.security.NonUniqueSecurityInfoException;
import org.eclipse.leshan.server.security.SecurityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

public class BlockchainStore implements EditableSecurityStore {

	static Logger log = LoggerFactory.getLogger(BlockchainStore.class);

	//String url = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32"; //Ropsten
	//String privateKey = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8"; //Private key of an account, Ropsten
	//String contractAddress = "0x0b65c81b465953fd25b29c0caffd2a448f0b948f"; //Ropsten

	String url = "HTTP://127.0.0.1:7545"; //Ganache
	String privateKey = "a701158005907d33a130caa07a2b7d811b409336ba14efca9a00b8593ec6feb9"; //Private key of an account, Ganache
	String contractAddress = "0x17EC1b6b63b5B477Aa38E5389e98765573Db5415"; //Ganache

	BigInteger gasPrice = new BigInteger("20000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	BootstrapStore contract;

	public BlockchainStore() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			contract = loadContract(web3j, credentials, contractAddress, gasProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SecurityInfo getByEndpoint(String endpoint) {
		System.out.println("BlockchainStore - getByEndpoint");
		SecurityInfo securityInfo = null;

		try {
			securityInfo = getClient(contract, endpoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return securityInfo;
	}

	@Override
	public SecurityInfo getByIdentity(String pskIdentity) {
		System.out.println("BlockchainStore - getByIdentity");

		try {
			String[] endpoints = getAllEndpoints(contract);

			for (String end : endpoints) {
				SecurityInfo securityInfoFor = getClient(contract, end);
				if(securityInfoFor.getIdentity().equals(pskIdentity)) {
					return securityInfoFor;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<SecurityInfo> getAll() {
		System.out.println("BlockchainStore - getAll");
		Collection<SecurityInfo> res = new ArrayList<SecurityInfo>();

		try {
			String[] endpoints = getAllEndpoints(contract);

			for (String end : endpoints) {
				SecurityInfo securityInfoFor = getClient(contract, end);
				res.add(securityInfoFor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public SecurityInfo add(SecurityInfo info) throws NonUniqueSecurityInfoException {
		System.out.println("BlockchainStore - add (Nothing to do)");
		return null;
	}

	@Override
	public SecurityInfo remove(String endpoint) {
		System.out.println("BlockchainStore - remove (Nothing to do)");
		return null;
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

	private SecurityInfo getClient(BootstrapStore contract, String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> clientConfig = contract.getClient(asciiToByte32(endpoint)).send();
		SecurityInfo securityInfo = SecurityInfo.newPreSharedKeyInfo(endpoint, byteToAscii(clientConfig.component5()), hexToByte(byteToAscii(clientConfig.component6())));
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getClient: "+ totalTime + " ms");
		return securityInfo;
	}

	private String[] getAllEndpoints(BootstrapStore contract) throws Exception {
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

	private byte[] asciiToByte32(String ascii){
		byte[] bytearray = new byte[32];
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
