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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.User;
import com.ivan.tfm.MainAppBackEnd.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	public List<User> getAllUsers() {
		List<User> res = new ArrayList<>();
		res = userService.getAll();
		return res;
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		userService.addUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{username}")
	public  ResponseEntity<HttpStatus> deleteUser(@PathVariable("username") String username) {
		userService.deleteUser(username);
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	
}
