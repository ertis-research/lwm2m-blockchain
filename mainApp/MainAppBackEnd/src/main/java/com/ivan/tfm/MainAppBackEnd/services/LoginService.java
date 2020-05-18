package com.ivan.tfm.MainAppBackEnd.services;

import org.springframework.stereotype.Service;

import com.ivan.tfm.MainAppBackEnd.beans.Credentials;

@Service
public class LoginService {

	public Credentials validateLogin(String wildcard, String password) {
		//TO-DO
		Credentials c = new Credentials("JTrillo", "trillo@uma.es", 1, "1234567890abcdef");
		return c;
	}
}
