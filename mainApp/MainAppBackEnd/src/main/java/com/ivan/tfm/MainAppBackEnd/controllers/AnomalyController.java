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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.tfm.MainAppBackEnd.beans.Anomaly;
import com.ivan.tfm.MainAppBackEnd.services.AnomalyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/anomalies")
public class AnomalyController {

	@Autowired
	AnomalyService anomalyService;

	@GetMapping("/")
	public List<Anomaly> getAllAnomalies() {
		List<Anomaly> res = new ArrayList<>();
		res = anomalyService.getAll();
		return res;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Anomaly> addAnomaly(@RequestBody Anomaly anomaly) {
		anomalyService.addAnomaly(anomaly);
		return new ResponseEntity<Anomaly>(anomaly, HttpStatus.OK);
	}
}
