package com.ivan.tfm.MainAppBackEnd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.AclEntry;
import com.ivan.tfm.MainAppBackEnd.services.AclService;
import com.ivan.tfm.MainAppBackEnd.utils.JwtUtility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/acl")
public class AclController {

	@Autowired
	AclService aclService;
	
	@GetMapping({"/{username}", "/{username}/filterByClient/{client}"})
	public ResponseEntity<List<AclEntry>> getUserEntries(@RequestHeader("Authorization") String auth,
			@PathVariable String username, @PathVariable(required=false) String client) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			List<AclEntry> res;
			if(client != null) {
				res = aclService.getUserEntriesByClient(username, client);
			} else {
				res = aclService.getUserEntries(username);
			}
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<HttpStatus> updateEntry(@RequestHeader("Authorization") String auth,
			@PathVariable("username") String username, @RequestBody AclEntry entry) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			try {
				aclService.updateEntry(username, entry);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		} else if(valid == 1){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
