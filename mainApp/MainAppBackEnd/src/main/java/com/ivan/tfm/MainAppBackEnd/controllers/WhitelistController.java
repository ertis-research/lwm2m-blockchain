package com.ivan.tfm.MainAppBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.EthereumAccount;
import com.ivan.tfm.MainAppBackEnd.services.AclService;
import com.ivan.tfm.MainAppBackEnd.services.AnomalyService;
import com.ivan.tfm.MainAppBackEnd.services.ClientService;
import com.ivan.tfm.MainAppBackEnd.services.UserService;
import com.ivan.tfm.MainAppBackEnd.utils.JwtUtility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/whitelist")
public class WhitelistController {
	
	@Autowired
	AclService aclService;
	
	@Autowired
	AnomalyService anomalyService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	UserService userService;
	
	@PutMapping("/{contract}")
	public ResponseEntity<HttpStatus> setPermission(@RequestHeader("Authorization") String auth,
			@PathVariable String contract, @RequestBody EthereumAccount ethereumAccount) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			try {
				switch(contract.toUpperCase()) {
					case "ACL":
						aclService.setPermission(ethereumAccount);
						break;
					case "ANOMALY":
						anomalyService.setPermission(ethereumAccount);
						break;
					case "CLIENT":
						clientService.setPermission(ethereumAccount);
					case "USER":
						userService.setPermission(ethereumAccount);
						break;
					default:
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
