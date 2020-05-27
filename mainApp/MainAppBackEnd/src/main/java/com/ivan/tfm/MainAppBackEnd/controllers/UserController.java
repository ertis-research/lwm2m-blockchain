package com.ivan.tfm.MainAppBackEnd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		if(JwtUtility.isValidToken(auth, 1)) {
			List<User> res = new ArrayList<>();
			res = userService.getAll();
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestHeader("Authorization") String auth, @RequestBody User user) {
		if(JwtUtility.isValidToken(auth, 1)) {
			userService.addUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@DeleteMapping("/delete/{username}")
	public  ResponseEntity<HttpStatus> deleteUser(@RequestHeader("Authorization") String auth, @PathVariable("username") String username) {
		if(JwtUtility.isValidToken(auth, 1)) {
			userService.deleteUser(username);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
}
