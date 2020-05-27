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

import com.ivan.tfm.MainAppBackEnd.beans.Client;
import com.ivan.tfm.MainAppBackEnd.services.ClientService;
import com.ivan.tfm.MainAppBackEnd.utils.JwtUtility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@GetMapping()
	public ResponseEntity<List<Client>> getAllClients(@RequestHeader("Authorization") String auth) {
		if(JwtUtility.isValidToken(auth, 1)) {
			List<Client> res = new ArrayList<>();
			res = clientService.getAllClients();
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> createClient(@RequestHeader("Authorization") String auth, @RequestBody Client bc) {
		if(JwtUtility.isValidToken(auth, 1)) {
			try {
				clientService.addClient(bc.getEndpoint(), bc.getUrl_bs(),bc.getId_bs(),bc.getKey_bs(), bc.getUrl_s(), bc.getId_s(), bc.getKey_s());
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				return  new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/remove/{endpoint}")
	public ResponseEntity<HttpStatus> removeClient(@RequestHeader("Authorization") String auth, @PathVariable("endpoint") String endpoint) {
		if(JwtUtility.isValidToken(auth, 1)) {
			try {
				System.out.println(endpoint);
				clientService.removeClient(endpoint);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				return  new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
