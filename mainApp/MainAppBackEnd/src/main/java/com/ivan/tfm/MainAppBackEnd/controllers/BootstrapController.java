package com.ivan.tfm.MainAppBackEnd.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.ivan.tfm.MainAppBackEnd.beans.BootstrapConfig;
import com.ivan.tfm.MainAppBackEnd.services.BlockchainService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bootstrap")
public class BootstrapController {


	BlockchainService blockchainService = new BlockchainService();

	@GetMapping("/endpoints")
	public List<String> getAllEndpoints() {
		List<String> res = new ArrayList<>();
		res = Arrays.asList(blockchainService.getAllEndpoints());
		return res;
	}
	
	@GetMapping("/clients")
	public List<BootstrapConfig> getAllClients() {
		List<BootstrapConfig> res = new ArrayList<>();
		res = blockchainService.getAllClients();
		return res;
	}

	@PostMapping("/addClient")
	public ResponseEntity<HttpStatus> createTodo(@RequestBody BootstrapConfig bc) {
		try {
			blockchainService.addClient(bc.getEndpoint(), bc.getUrl_bs(),bc.getId_bs(),bc.getKey_bs(), bc.getUrl_s(), bc.getId_s(), bc.getKey_s());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return  new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/removeClient/{endpoint}")
	public ResponseEntity<HttpStatus> removeClient(@PathVariable("endpoint") String endpoint) {
		try {
			System.out.println(endpoint);
			blockchainService.removeClient(endpoint);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return  new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
