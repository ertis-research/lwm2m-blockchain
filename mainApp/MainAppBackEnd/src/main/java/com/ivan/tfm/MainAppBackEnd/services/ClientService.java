package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;

import com.ivan.tfm.MainAppBackEnd.beans.Client;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class ClientService {

	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "BootstrapStore";

	private int existsClient(String endpoint) throws Exception{
		long startTime = System.currentTimeMillis();
		BigInteger exist = this.blockchainService.getClient_contract().existsClient(Converter.asciiToByte32(endpoint)).send();
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

			TransactionReceipt txReceipt = this.blockchainService.getClient_contract().addClient(endpointByte,
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
				TransactionReceipt txReceipt = this.blockchainService.getClient_contract().removeClient(Converter.asciiToByte32(endpoint)).send();
				this.blockchainService.logTransaction(txReceipt, "removeClient", this.contractName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long totalTime = ((endTime - startTime));
		System.out.println("removeClient: "+ totalTime + " ms");
	}
	
	public List<Client> getAllClients(){
		List<Client> clients = new ArrayList<Client>();

		try {
			Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> response = this.blockchainService.getClient_contract().getAllClients().send();
			clients = tuple7ToList(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clients;
	}

	private List<Client> tuple7ToList(Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> tuple){
		List<Client> clients = new ArrayList<Client>();

		List<byte[]> endpoints = tuple.component1();
		List<byte[]> urls_bs = tuple.component2();
		List<byte[]> ids_bs = tuple.component3();
		List<byte[]> keys_bs = tuple.component4();
		List<byte[]> urls_s = tuple.component5();
		List<byte[]> ids_s = tuple.component6();
		List<byte[]> keys_s = tuple.component7();

		for (int i = 0; i < tuple.component1().size(); i++) {
			String endpoint = Converter.byteToAscii(endpoints.get(i));
			String url_bs = Converter.byteToAscii(urls_bs.get(i));
			String id_bs = Converter.byteToAscii(ids_bs.get(i));
			String key_bs = Converter.byteToAscii(keys_bs.get(i));
			String url_s = Converter.byteToAscii(urls_s.get(i));
			String id_s = Converter.byteToAscii(ids_s.get(i));
			String key_s = Converter.byteToAscii(keys_s.get(i));

			Client client = new Client(endpoint, url_bs, id_bs, key_bs, url_s, id_s, key_s);
			clients.add(client);
		}
		return clients;
	}

}
