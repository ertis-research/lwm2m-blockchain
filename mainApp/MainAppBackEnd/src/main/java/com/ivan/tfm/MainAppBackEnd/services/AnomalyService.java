package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.tfm.MainAppBackEnd.beans.Anomaly;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;
import com.ivan.tfm.MainAppBackEnd.wrappers.AnomalyStore;

@Service
public class AnomalyService {

	static Logger log = LoggerFactory.getLogger(AnomalyService.class);

	//String url = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32"; //Ropsten
	//String privateKey = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8"; //Private key of an account, Ropsten
	//String contractAddress = "0x0b65c81b465953fd25b29c0caffd2a448f0b948f"; //Ropsten

	String url = "HTTP://127.0.0.1:7545"; //Ganache
	String privateKey = "a701158005907d33a130caa07a2b7d811b409336ba14efca9a00b8593ec6feb9"; //Private key of an account, Ganache
	String contractAddress = "0x594fd558d0a4f6f4f5d23e291f6e06cc13a547fc"; //Ganache

	BigInteger gasPrice = new BigInteger("20000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	AnomalyStore contract;

	//List<String> addresses = new ArrayList<String>(); //to deploy
	public AnomalyService() {
		try {
			web3j = connect(url);
			Credentials credentials = createCredentials(privateKey);
			//contract = deployContract(web3j, credentials, gasProvider, addresses); //to deploy
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
	private AnomalyStore loadContract(Web3j web3j, Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		AnomalyStore contract = AnomalyStore.load(address, web3j, credentials, provider);
		log.info("Smart contract loaded");
		return contract;
	}

	// Deploy smart contract
	private AnomalyStore deployContract(Web3j web3j, Credentials credentials, ContractGasProvider provider, List<String> addresses) throws Exception {
		AnomalyStore contract = AnomalyStore.deploy(web3j, credentials, provider, addresses).send();
		String contractAddress = contract.getContractAddress();
		log.info("Smart contract deployed to address " + contractAddress);
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

	public void addAnomaly(Anomaly a){
		try {
			BigInteger ts  = BigInteger.valueOf(a.getTimestamp());
			byte[] endpoint = Converter.asciiToByte32(a.getEndpoint());
			BigInteger emergency = BigInteger.valueOf(a.getEmergencyLevel());
			BigInteger temp = BigInteger.valueOf(Math.round(100 * a.getTemperature()));

			TransactionReceipt txReceipt = contract.addAnomaly(ts, endpoint, emergency, temp).send();
			logTransaction(txReceipt, "addAnomaly");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
