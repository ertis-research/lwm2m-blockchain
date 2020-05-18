package com.ivan.tfm.MainAppBackEnd.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ivan.tfm.MainAppBackEnd.beans.User;

@Service
public class UserService {
	
	List<User> users;
	
	public UserService() {
		users = new ArrayList<User>();
		User user1 = new User("admin", "admin@test.com", "admin", 1);
		User user2 = new User("ivan", "ivan@test.com", "123456", 2);
		User user3 = new User("noe", "noe@test.com", "654321", 3);
		users.add(user1);
		users.add(user2);
		users.add(user3);
	}
	
	public List<User> getAll(){
		return users;
	}
	
	public void addUser(User u) {
		if(!existByUsername(u.getUsername())) {
			users.add(u);
		}
	}
	
	public void deleteUser(String username) {
		int pos = 0;
		boolean exist = false;
		while(exist==false && pos<users.size()) {
			if(users.get(pos).getUsername().equals(username)){
				exist = true;
				users.remove(pos);
			}
			pos++;
		}
	}
	
	public User getByUsername(String username) {
		User user = null;
		int pos = 0;
		boolean exist = false;
		while(exist==false && pos<users.size()) {
			if(users.get(pos).getUsername().equals(username)){
				exist = true;
				user = users.get(pos);
			}
			pos++;
		}
		return user;
	}
	
	public boolean existByUsername(String username) {
		int pos = 0;
		boolean exist = false;
		while(exist==false && pos<users.size()) {
			if(users.get(pos).getUsername().equals(username)){
				exist = true;
			}
			pos++;
		}
		return exist;
	}

}
