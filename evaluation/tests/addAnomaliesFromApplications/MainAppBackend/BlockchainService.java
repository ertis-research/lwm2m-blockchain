package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.tfm.MainAppBackEnd.wrappers.AnomalyStore;
import com.ivan.tfm.MainAppBackEnd.wrappers.ClientStore;
import com.ivan.tfm.MainAppBackEnd.wrappers.UserStore;

@Service
public class BlockchainService {
	
	static Logger log = LoggerFactory.getLogger(BlockchainService.class);

	// Ropsten (Ethereum testnet)
	String URL = ""; //TO COMPLETE USING INFURA
	String PRIVATE_KEY = ""; //TO COMPLETE
	private final String CLIENT_STORE_ADDR = "0xb622de47189da342ec357b9be5da7eb52fc7c84e";
	private final String ANOMALY_STORE_ADDR = "0x409a973ba7f6bc78fd0d9365179d24459ec02615";
	private final String USER_STORE_ADDR = "0xe1cf47b70c5619540f05231ffec7203e33e0c66b";
	
	private Web3j web3j;
	private Credentials credentials;
	
	private final BigInteger gasPrice = new BigInteger("40000000000");
	private final BigInteger gasLimit = new BigInteger("75388");
	private final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	
	private ClientStore client_contract;
	private AnomalyStore anomaly_contract;
	private UserStore user_contract;
	
	public BlockchainService() {
		try {
			this.web3j = connect(URL);
			this.credentials = createCredentials(PRIVATE_KEY);
			this.client_contract = loadContract1();
			this.anomaly_contract = loadContract2();
			this.user_contract = loadContract3();
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
	
	// Load smart contract 'ClientStore'
	private ClientStore loadContract1() throws Exception {
		ClientStore contract = ClientStore.load(this.CLIENT_STORE_ADDR, this.web3j, this.credentials, this.gasProvider);
		log.info("Smart contract 'ClientStore' loaded");
		return contract;
	}
	
	// Load smart contract 'AnomalyStore'
	private AnomalyStore loadContract2() throws Exception {
		AnomalyStore contract = AnomalyStore.load(this.ANOMALY_STORE_ADDR, this.web3j, this.credentials, this.gasProvider);
		log.info("Smart contract 'AnomalyStore' loaded");
		return contract;
	}
	
	// Load smart contract 'UserStore'
	private UserStore loadContract3() throws Exception {
		UserStore contract = UserStore.load(this.USER_STORE_ADDR, this.web3j, this.credentials, this.gasProvider);
		log.info("Smart contract 'UserStore' loaded");
		return contract;
	}

	public ClientStore getClient_contract() {
		return client_contract;
	}

	public AnomalyStore getAnomaly_contract() {
		return anomaly_contract;
	}
	
	public AnomalyStore getAnomaly_contract_public(String privateKey) throws Exception{
		Credentials credentials1 = createCredentials(privateKey);
		AnomalyStore contract = AnomalyStore.load(this.ANOMALY_STORE_ADDR, this.web3j, credentials1, this.gasProvider);
		log.info("Smart contract 'AnomalyStorePublic' loaded");
		return contract;
	}

	public UserStore getUser_contract() {
		return user_contract;
	}
	
	public void logTransaction(TransactionReceipt txReceipt, String txName, String contractName) {
		String txHash = txReceipt.getTransactionHash();
		BigInteger blockNumber = txReceipt.getBlockNumber();
		String blockHash = txReceipt.getBlockHash();
		BigInteger gasUsed = txReceipt.getGasUsed();
		log.info("EXECUTED TRANSACTION '" + txName + "' on contract '" + contractName + "'");
		log.info("Tx Hash: " + txHash);
		log.info("Block Number: " + blockNumber);
		log.info("Block Hash: " + blockHash);
		log.info("Gas Used: " + gasUsed);
	}
}
