package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import com.ivan.tfm.MainAppBackEnd.beans.AclEntry;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class AclService {

	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "AclStore";
	
	public List<AclEntry> getUserEntries(String username) {
		List<AclEntry> entries = new ArrayList<AclEntry>();
		
		try {
			byte[] usnm = Converter.asciiToByte32(username);
			Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>> response =
					this.blockchainService.getAcl_contract().getUserEntries(usnm).send();
			entries = tuple4ToList(response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public List<AclEntry> getUserEntriesByClient(String username, String client_name) {
		List<AclEntry> entries = new ArrayList<AclEntry>();
		
		try {
			byte[] usnm = Converter.asciiToByte32(username);
			byte[] cl_nm = Converter.asciiToByte32(client_name);
			Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>> response =
					this.blockchainService.getAcl_contract().getUserEntriesByClient(usnm, cl_nm).send();
			entries = tuple4ToList(response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	public void updateEntry(String username, AclEntry entry) {
		try {
			byte[] usnm = Converter.asciiToByte32(username);
			byte[] client_name = Converter.asciiToByte32(entry.getClient_name());
			BigInteger object_id = BigInteger.valueOf(entry.getObject_id());
			BigInteger resource_id = BigInteger.valueOf(entry.getResource_id());
			BigInteger permission = BigInteger.valueOf(entry.getPermission());
			TransactionReceipt txReceipt = this.blockchainService.getAcl_contract()
					.updateEntry(usnm, client_name, object_id, resource_id, permission).send();
			this.blockchainService.logTransaction(txReceipt, "updateEntry", this.contractName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//PRIVATE FUNCTIONS
	private List<AclEntry> tuple4ToList(Tuple4<List<byte[]>, List<BigInteger>, List<BigInteger>, List<BigInteger>> tuple) {
		List<AclEntry> entries = new ArrayList<AclEntry>();
		List<byte[]> client_names = tuple.component1();
		List<BigInteger> object_ids = tuple.component2();
		List<BigInteger> resource_ids = tuple.component3();
		List<BigInteger> permissions = tuple.component4();
		
		for(int i=0; i<tuple.component1().size(); i++) {
			String client_name = Converter.byteToAscii(client_names.get(i));
			// Empty client name means there are no more entries 
			if(client_name.equals("")) return entries;
			int object_id = object_ids.get(i).intValue();
			int resource_id = resource_ids.get(i).intValue();
			int permission = permissions.get(i).intValue();
			AclEntry entry = new AclEntry(client_name, object_id, resource_id, permission);
			entries.add(entry);
		}
		return entries;
	}
}
