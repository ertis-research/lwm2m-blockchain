package com.ivan.ServerLeshan.service;

import org.springframework.stereotype.Service;
import com.ivan.ServerLeshan.bean.Credentials;

@Service
public class LoginService {

	public Credentials validateLogin(String wildcard, String password) {
		//TO-DO
		Credentials c = new Credentials("ivan", "ivanalba@uma.es", 1, "1234567890abcdef");
		return c;
	}
}
