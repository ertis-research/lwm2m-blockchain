package com.ivan.tfm.MainAppBackEnd.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivan.tfm.MainAppBackEnd.beans.AclEntry;

@Service
public class AclService {

	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "AclStore";
	
	public boolean getUserPermission(String username, String client_name, int object_id, int resource_id) {
		// TODO: use smart contract
		return true;
	}
	
	public List<AclEntry> getUserEntries(String username) {
		// TODO: use smart contract
		List<AclEntry> entries = new ArrayList<AclEntry>();
		
		entries.add(new AclEntry("client1", 1, 1, 7));
		return entries;
	}
	
	public List<AclEntry> getUserEntriesByClient(String username, String client_name) {
		// TODO: use smart contract
		List<AclEntry> entries = new ArrayList<AclEntry>();
		
		entries.add(new AclEntry("client1", 1, 1, 7));
		return entries;
	}
	
	public void updateEntry(String username, String client_name, int object_id, int resource_id, int permission) {
		// TODO: use smart contract
	}
}
