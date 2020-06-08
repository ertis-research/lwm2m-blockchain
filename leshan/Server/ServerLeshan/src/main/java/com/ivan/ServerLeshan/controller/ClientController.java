package com.ivan.ServerLeshan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.ServerLeshan.bean.Client;
import com.ivan.ServerLeshan.service.LeshanServerService;
import com.ivan.ServerLeshan.utils.JwtUtility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/server")
public class ClientController {

	@Autowired
	LeshanServerService leshanServerService;
	
	@GetMapping("/")
	public ResponseEntity<List<Client>> getAllClients(@RequestHeader("Authorization") String auth) {
		int valid = JwtUtility.isValidToken(auth);
		if(valid == 0) {
			List<Client> connectedClients = leshanServerService.getAllConnectedClients();
			return new ResponseEntity<>(connectedClients, HttpStatus.OK);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{endpoint}")
	public ResponseEntity<Client> getValue(@RequestHeader("Authorization") String auth, @PathVariable("endpoint") String endpoint) {
		int valid = JwtUtility.isValidToken(auth);
		if(valid == 0) {
			Client c = leshanServerService.getValueFromClient(endpoint);
			return new ResponseEntity<>(c, HttpStatus.OK);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}