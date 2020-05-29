package com.ivan.ServerLeshan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ivan.ServerLeshan.bean.Credentials;
import com.ivan.ServerLeshan.bean.User;

@Service
public class LoginService {
	
	List<User> users;
	
	public LoginService() {
		users = new ArrayList<User>();
		User user1 = new User("admin", "admin@test.com", "admin", 1);
		User user2 = new User("ivan", "ivan@test.com", "123456", 2);
		User user3 = new User("noe", "noe@test.com", "654321", 3);
		users.add(user1);
		users.add(user2);
		users.add(user3);
	}
	
	public Credentials validateLogin(String wildcard, String password) {
		for (User user : users) {
			if(user.getEmail().equals(wildcard) && user.getPassword().equals(password)) {
				Credentials c = new Credentials(user.getUsername(), user.getEmail(), user.getRole(), "1234567890abcdef");
				return c;
			}
		}
		return null;
	}
}
