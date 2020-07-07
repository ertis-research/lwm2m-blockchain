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
	private final String URL = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32";
	private final String PRIVATE_KEY = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8";
	private final String CLIENT_STORE_ADDR = "0x6c8568e24548115439174e86d98fb8d7d3de2c4b";
	private final String ANOMALY_STORE_ADDR = "0x1c209d6e3222f30a52fd2144f6ec4ea11c471d9c";
	private final String USER_STORE_ADDR = "0xcfc79c76fe6c55445fa9301be5b2313dbba2d9ea";
	
	
	// Ganache (local network)
	/*
	private final String URL = "http://127.0.0.1:7545";
	private final String PRIVATE_KEY = "";
	private final String CLIENT_STORE_ADDR = "";
	private final String ANOMALY_STORE_ADDR = "";
	private final String USER_STORE_ADDR = "";
	*/
	
	private Web3j web3j;
	private Credentials credentials;
	
	private final BigInteger gasPrice = new BigInteger("20000000000");
	private final BigInteger gasLimit = new BigInteger("4712388");
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
