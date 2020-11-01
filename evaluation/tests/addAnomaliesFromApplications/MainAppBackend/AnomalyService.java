package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import com.ivan.tfm.MainAppBackEnd.beans.Anomaly;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class AnomalyService {

	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "AnomalyStore";

	public List<Anomaly> getAll(){
		List<Anomaly> anomalies = new ArrayList<Anomaly>();

		try {
			Tuple4<List<BigInteger>, List<byte[]>, List<BigInteger>, List<BigInteger>> response = this.blockchainService.getAnomaly_contract().getAllAnomalies().send();
			anomalies = tuple4ToList(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return anomalies;
	}

	public void addAnomaly(Anomaly a){
		try {
			BigInteger ts  = BigInteger.valueOf(a.getTimestamp());
			byte[] endpoint = Converter.asciiToByte32(a.getEndpoint());
			BigInteger emergency = BigInteger.valueOf(a.getEmergencyLevel());
			BigInteger temp = BigInteger.valueOf(Math.round(100 * a.getTemperature()));

			TransactionReceipt txReceipt = this.blockchainService.getAnomaly_contract().addAnomaly(ts, endpoint, emergency, temp).send();
			this.blockchainService.logTransaction(txReceipt, "addAnomaly", this.contractName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//PRIVATE FUNCTIONS
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
	
	public void addAnomaly(Anomaly a, String privateKey){
		try {
			BigInteger ts  = BigInteger.valueOf(a.getTimestamp());
			byte[] endpoint = Converter.asciiToByte32(a.getEndpoint());
			BigInteger emergency = BigInteger.valueOf(a.getEmergencyLevel());
			BigInteger temp = BigInteger.valueOf(Math.round(100 * a.getTemperature()));
			TransactionReceipt txReceipt = this.blockchainService.getAnomaly_contract_public(privateKey).addAnomaly(ts, endpoint, emergency, temp).send();
			this.blockchainService.logTransaction(txReceipt, "addAnomaly", this.contractName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
