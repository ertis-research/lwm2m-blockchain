package com.ivan.ServerLeshan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivan.ServerLeshan.bean.Client;
import com.ivan.ServerLeshan.service.LeshanServerService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/server")
public class LeshanServerController {

	@Autowired
	LeshanServerService leshanServerService;
	
	@GetMapping("/")
	public List<Client> getAllClients() {
		List<Client> connectedClients = leshanServerService.getAllConnectedClients();
		return connectedClients;
	}
	
	@GetMapping("/{endpoint}")
	public Client getValue(@PathVariable("endpoint") String endpoint) {
		System.out.println("get temperature value from " +endpoint);
		Client c = leshanServerService.getValueFromClient(endpoint);
		return c;
	}
}