package com.ivan.tfm.MainAppBackEnd.services;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.tuples.generated.Tuple3;

import com.ivan.tfm.MainAppBackEnd.beans.Credentials;
import com.ivan.tfm.MainAppBackEnd.utils.Converter;

@Service
public class LoginService {

	@Autowired
	BlockchainService blockchainService;
	
	public Credentials validateLogin(String wildcard, String password) {
		Credentials c = new Credentials("", "", 0, "");
		try {
			byte[] wildcard_bytes = Converter.asciiToByte32(wildcard);
			byte[] password_bytes = Converter.asciiToByte32(password);
			Tuple3<byte[], byte[], BigInteger> response = 
					this.blockchainService.getUser_contract().validateLogin(wildcard_bytes, password_bytes).send();
			c = this.tuple3ToList(response);
			return c;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private Credentials tuple3ToList(Tuple3<byte[], byte[], BigInteger> tuple) {
		String username = Converter.byteToAscii(tuple.component1());
		String email = Converter.byteToAscii(tuple.component2());
		int role = tuple.component3().intValue();
		
		Credentials c = new Credentials(username, email, role, "");
		return c;
	}
}
