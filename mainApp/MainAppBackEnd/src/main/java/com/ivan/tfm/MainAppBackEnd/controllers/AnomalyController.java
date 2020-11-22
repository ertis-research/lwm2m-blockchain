package com.ivan.tfm.MainAppBackEnd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.Anomaly;
import com.ivan.tfm.MainAppBackEnd.beans.EthereumAccount;
import com.ivan.tfm.MainAppBackEnd.services.AnomalyService;
import com.ivan.tfm.MainAppBackEnd.utils.JwtUtility;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/anomalies")
public class AnomalyController {

	@Autowired
	AnomalyService anomalyService;

	@GetMapping()
	public ResponseEntity<List<Anomaly>> getAllAnomalies(@RequestHeader("Authorization") String auth) {
		int valid = JwtUtility.isValidToken(auth, 3);
		if(valid == 0) {
			List<Anomaly> res = new ArrayList<>();
			res = anomalyService.getAll();
			return new ResponseEntity<>(res, HttpStatus.OK);
		}else if(valid == 1){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Anomaly> addAnomaly(@RequestHeader("Authorization") String auth, @RequestBody Anomaly anomaly) {
		int valid = JwtUtility.isValidToken(auth, 2);
		if(valid == 0) {
			anomalyService.addAnomaly(anomaly,anomaly.getPrivateKey());
			return new ResponseEntity<Anomaly>(anomaly, HttpStatus.CREATED);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/setPermission")
	public ResponseEntity<EthereumAccount> setPermission(@RequestHeader("Authorization") String auth, @RequestBody EthereumAccount ethereumAccount) {
		int valid = JwtUtility.isValidToken(auth, 1);
		if(valid == 0) {
			anomalyService.setPermission(ethereumAccount);
			return new ResponseEntity<EthereumAccount>(ethereumAccount, HttpStatus.CREATED);
		}else if(valid == 1) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else if(valid == 2) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
