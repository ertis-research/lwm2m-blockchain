package com.ivan.ServerLeshan.service;

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
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.ServerLeshan.utils.Converter;
import com.ivan.ServerLeshan.wrappers.ClientStore;

public class ClientStoreService implements EditableSecurityStore {

	static Logger log = LoggerFactory.getLogger(ClientStoreService.class);

	String url = ""; //TO COMPLETE USING INFURA
	String privateKey = ""; //TO COMPLETE
	String contractAddress = "0x6c8568e24548115439174e86d98fb8d7d3de2c4b"; //Ropsten

	BigInteger gasPrice = new BigInteger("40000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	ClientStore contract;

	public ClientStoreService() {
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
		Collection<SecurityInfo> clients = getAll();

		for (SecurityInfo securityInfo : clients) {
			if(securityInfo.getIdentity().equals(pskIdentity)) {
				return securityInfo;
			}
		}

		return null;
	}

	@Override
	public Collection<SecurityInfo> getAll() {
		Collection<SecurityInfo> securityInfos = new ArrayList<SecurityInfo>();

		try {
			Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> response = contract.getAllClients().send();
			securityInfos = tuple7ToSecurityInfoCollection(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return securityInfos;
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
	private ClientStore loadContract(Web3j web3j, Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		ClientStore contract = ClientStore.load(address, web3j, credentials, provider);
		log.info("Smart contract loaded");
		return contract;
	}

	private SecurityInfo getClient(ClientStore contract, String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> clientConfig = contract.getClient(Converter.asciiToByte32(endpoint)).send();
		SecurityInfo securityInfo = SecurityInfo.newPreSharedKeyInfo(endpoint, Converter.byteToAscii(clientConfig.component5()), Converter.hexToByte(Converter.byteToAscii(clientConfig.component6())));
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("getClient: "+ totalTime + " ms");
		return securityInfo;
	}

	private Collection<SecurityInfo> tuple7ToSecurityInfoCollection(Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> tuple){
		Collection<SecurityInfo> securityInfos = new ArrayList<SecurityInfo>();

		List<byte[]> endpoints = tuple.component1();
		List<byte[]> ids_s = tuple.component6();
		List<byte[]> keys_s = tuple.component7();

		for (int i = 0; i < tuple.component1().size(); i++) {			
			String endpoint = Converter.byteToAscii(endpoints.get(i));
			String id_s = Converter.byteToAscii(ids_s.get(i));
			String key_s = Converter.byteToAscii(keys_s.get(i));
			SecurityInfo securityInfo = SecurityInfo.newPreSharedKeyInfo(endpoint, id_s, Converter.hexToByte(key_s));
			securityInfos.add(securityInfo);
		}
		return securityInfos;
	}
}
