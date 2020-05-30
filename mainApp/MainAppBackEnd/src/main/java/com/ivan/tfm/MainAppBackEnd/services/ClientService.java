package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;

import com.ivan.tfm.MainAppBackEnd.beans.Client;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class ClientService {

	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "BootstrapStore";

	private int existsClient(String endpoint) throws Exception{
		long startTime = System.currentTimeMillis();
		BigInteger exist = this.blockchainService.getBs_contract().existsClient(Converter.asciiToByte32(endpoint)).send();
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("existClient: "+ totalTime + " ms");
		return exist.intValue();
	}

	public void addClient(String endpoint,
			String url_bs, String id_bs, String key_bs,
			String url_s, String id_s, String key_s) throws Exception{

		long startTime = System.currentTimeMillis();
		if(existsClient(endpoint) == -1) {
			byte[] endpointByte = Converter.asciiToByte32(endpoint);
			byte[] url_bsByte = Converter.asciiToByte32(url_bs);
			byte[] id_bsByte = Converter.asciiToByte32(id_bs);
			byte[] key_bsByte = Converter.asciiToByte32(key_bs);
			byte[] url_sByte = Converter.asciiToByte32(url_s);
			byte[] id_sByte = Converter.asciiToByte32(id_s);
			byte[] key_sByyte = Converter.asciiToByte32(key_s);

			TransactionReceipt txReceipt = this.blockchainService.getBs_contract().addClient(endpointByte,
					url_bsByte, id_bsByte, key_bsByte,
					url_sByte, id_sByte, key_sByyte).send();
			this.blockchainService.logTransaction(txReceipt, "addClient", this.contractName);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("addClient: "+ totalTime + " ms");
	}

	public void removeClient(String endpoint) throws Exception {
		long startTime = System.currentTimeMillis();
		
		try {
			if(existsClient(endpoint) != -1) {
				TransactionReceipt txReceipt = this.blockchainService.getBs_contract().removeClient(Converter.asciiToByte32(endpoint)).send();
				this.blockchainService.logTransaction(txReceipt, "removeClient", this.contractName);
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
			list = this.blockchainService.getBs_contract().getAllClients().send();
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

	private Client getClient(String endpoint) throws Exception {
		//long startTime = System.currentTimeMillis();
		Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> response = this.blockchainService.getBs_contract().getClient(Converter.asciiToByte32(endpoint)).send();
		Client client = new Client(endpoint, response);
		//long endTime = System.currentTimeMillis();
		//long totalTime = ((endTime - startTime));
		//System.out.println("getClient: "+ totalTime + " ms");
		return client;
	}

	public List<Client> getAllClients(){
		long startTime = System.currentTimeMillis();
		List<Client> res = new ArrayList<Client>();
		
		try {
			String[] endpoints = getAllEndpoints();
			for (String end : endpoints) {
				Client client = getClient(end);
				res.add(client);
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
