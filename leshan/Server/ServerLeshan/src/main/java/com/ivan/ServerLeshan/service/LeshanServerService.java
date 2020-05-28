package com.ivan.ServerLeshan.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Service;

import com.ivan.ServerLeshan.bean.Client;

@Service
public class LeshanServerService {

	boolean supportDeprecatedCiphers = false;

	String secureLocalAddress= "0.0.0.0";
	int secureLocalPort = 5784;

	LeshanServer leshanServer;

	public LeshanServerService() {
		leshanServer = configureLeshanServer().build();
		leshanServer.start();
	}

	public LeshanServer getLeshanServer() {
		return leshanServer;
	}
	
	public void startLeshanServer() {
		leshanServer.start();
	}
	
	private LeshanServerBuilder configureLeshanServer() {
		LeshanServerBuilder builder = new LeshanServerBuilder();
		builder.disableUnsecuredEndpoint();
		builder.setLocalSecureAddress(secureLocalAddress, secureLocalPort);
		builder.setEncoder(new DefaultLwM2mNodeEncoder());
		LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
		builder.setDecoder(decoder);

		// Create CoAP Config
		NetworkConfig coapConfig;
		File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
		if (configFile.isFile()) {
			coapConfig = new NetworkConfig();
			coapConfig.load(configFile);
		} else {
			coapConfig = LeshanServerBuilder.createDefaultNetworkConfig();
			coapConfig.store(configFile);
		}
		builder.setCoapConfig(coapConfig);

		// Create DTLS Config
		DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
		dtlsConfig.setRecommendedCipherSuitesOnly(!supportDeprecatedCiphers);

		// Set DTLS Config
		builder.setDtlsConfig(dtlsConfig);

		// Set securityStore & registrationStore
		BlockchainStore securityStore = new BlockchainStore();
		builder.setSecurityStore(securityStore);

		return builder;
	}
	
	public List<Client> getAllConnectedClients(){
		List<Client> connectedClients = new ArrayList<Client>();
		Iterator<Registration> registrations = leshanServer.getRegistrationService().getAllRegistrations();
		while(registrations.hasNext()) {
			Registration element = registrations.next();
			long registration = element.getRegistrationDate().getTime();
			long update = element.getLastUpdate().getTime();
			connectedClients.add(new Client(element.getEndpoint(),registration,update));
		}
		return connectedClients;
	}
	
	public Client getValueFromClient(String endpoint) {
		
		Registration registration = leshanServer.getRegistrationService().getByEndpoint(endpoint);
		Client c = new Client(endpoint, registration.getRegistrationDate().getTime(), registration.getLastUpdate().getTime());
				
		try {
			ReadResponse response = leshanServer.send(registration, new ReadRequest(7,0,0));
			if(response.isSuccess()) {
				Object value = ((LwM2mResource)response.getContent()).getValue();
				c.setValue(Double.valueOf(value.toString()));
				c.setValueTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
			}else {
				System.out.println("Failed to read:" + response.getCode() + " " + response.getErrorMessage());
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return c;
	}
}
