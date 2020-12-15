package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import com.ivan.tfm.MainAppBackEnd.beans.EthereumAccount;
import com.ivan.tfm.MainAppBackEnd.beans.User;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class UserService {
	
	@Autowired
	BlockchainService blockchainService;
	
	private final String contractName = "UserStore";
	
	public List<User> getAll(){
		List<User> users = new ArrayList<User>();
		
		try {
			Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>> response =
					this.blockchainService.getUser_contract().getAllUsers().send();
			users = this.tuple4ToList(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public void addUser(User u) {
		try {
			if(this.existsUser(u.getUsername()) == -1) {
				byte[] username = Converter.asciiToByte32(u.getUsername());
				byte[] email = Converter.asciiToByte32(u.getEmail());
				byte[] password = Converter.asciiToByte32(u.getPassword());
				BigInteger role = BigInteger.valueOf(u.getRole());
				
				TransactionReceipt txReceipt = this.blockchainService.getUser_contract()
						.addUser(username, email, password, role).send();
				this.blockchainService.logTransaction(txReceipt, "addUser", this.contractName);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(User u) {
		try {
			if(this.existsUser(u.getUsername()) != -1) {
				byte[] username = Converter.asciiToByte32(u.getUsername());
				byte[] email = Converter.asciiToByte32(u.getEmail());
				byte[] password = Converter.asciiToByte32(u.getPassword());
				BigInteger role = BigInteger.valueOf(u.getRole());
				
				TransactionReceipt txReceipt = this.blockchainService.getUser_contract()
						.updateUser(username, email, password, role).send();
				this.blockchainService.logTransaction(txReceipt, "updateUser", this.contractName);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPermission(EthereumAccount ethereumAccount) {
		try {
			TransactionReceipt txReceipt = this.blockchainService.getUser_contract()
					.setPermission(ethereumAccount.getAddress(), ethereumAccount.isPermission()).send();
			this.blockchainService.logTransaction(txReceipt, "setPermissioN", contractName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//PRIVATE FUNCTIONS
	private List<User> tuple4ToList(Tuple4<List<byte[]>, List<byte[]>, List<byte[]>, List<BigInteger>> tuple) {
		List<User> users = new ArrayList<User>();
		List<byte[]> usernames = tuple.component1();
		List<byte[]> emails = tuple.component2();
		List<byte[]> passwords = tuple.component3();
		List<BigInteger> roles = tuple.component4();
		
		for (int i=0; i<tuple.component1().size(); i++) {
			String username = Converter.byteToAscii(usernames.get(i));
			String email = Converter.byteToAscii(emails.get(i));
			String password = Converter.byteToAscii(passwords.get(i));
			int role = roles.get(i).intValue();
			User user = new User(username, email, password, role);
			users.add(user);
		}
		return users;
	}
	
	private int existsUser(String username) throws Exception{
		BigInteger exists = this.blockchainService.getUser_contract()
				.existsUser(Converter.asciiToByte32(username)).send();
		return exists.intValue();
	}

}
