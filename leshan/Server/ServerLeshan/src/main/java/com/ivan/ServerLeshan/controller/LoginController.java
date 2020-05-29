package com.ivan.ServerLeshan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.ServerLeshan.bean.Credentials;
import com.ivan.ServerLeshan.bean.User;
import com.ivan.ServerLeshan.service.LoginService;
import com.ivan.ServerLeshan.utils.JwtUtility;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping
	public ResponseEntity<Credentials> validateLogin(@RequestBody User user) {
		String wildcard = user.getEmail();
		String password = user.getPassword();
		Credentials credentials = loginService.validateLogin(wildcard, password);
		
		if(credentials!=null && credentials.getRole() > 0 && credentials.getRole() < 3) {
			credentials.setToken(JwtUtility.generateToken(credentials));
			return new ResponseEntity<>(credentials, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}