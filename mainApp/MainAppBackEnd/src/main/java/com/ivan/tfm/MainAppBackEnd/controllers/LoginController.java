package com.ivan.tfm.MainAppBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.Credentials;
import com.ivan.tfm.MainAppBackEnd.beans.User;
import com.ivan.tfm.MainAppBackEnd.services.LoginService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping("/")
	public ResponseEntity<Credentials> validateLogin(@RequestBody User user) {
		String wildcard = user.getEmail();
		String password = user.getPassword();
		
		Credentials credentials = loginService.validateLogin(wildcard, password);
		
		if(credentials.getRole() != 0) {
			return new ResponseEntity<>(credentials, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
