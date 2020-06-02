package com.ivan.tfm.MainAppBackEnd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.User;
import com.ivan.tfm.MainAppBackEnd.services.UserService;
import com.ivan.tfm.MainAppBackEnd.utils.JwtUtility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String auth) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			List<User> res = new ArrayList<>();
			res = userService.getAll();
			return new ResponseEntity<>(res, HttpStatus.OK);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestHeader("Authorization") String auth, @RequestBody User user) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			userService.addUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/update")
	public  ResponseEntity<HttpStatus> updateUser(@RequestHeader("Authorization") String auth, @RequestBody User user) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			userService.updateUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
}
