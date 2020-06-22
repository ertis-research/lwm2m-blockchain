package com.ivan.ServerLeshan.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mNode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.VersionedModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ivan.ServerLeshan.bean.Client;
import com.ivan.ServerLeshan.json.LwM2mNodeDeserializer;
import com.ivan.ServerLeshan.json.LwM2mNodeSerializer;
import com.ivan.ServerLeshan.json.RegistrationSerializer;
import com.ivan.ServerLeshan.json.ResponseSerializer;

@Service
public class LeshanServerService {

	private final Gson gson;
	boolean supportDeprecatedCiphers = false;
	String secureLocalAddress= "0.0.0.0";
	int secureLocalPort = 5784;

	LeshanServer leshanServer;

	public LeshanServerService() {
		GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(Registration.class, new RegistrationSerializer(leshanServer.getPresenceService()));
        gsonBuilder.registerTypeHierarchyAdapter(LwM2mResponse.class, new ResponseSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(LwM2mNode.class, new LwM2mNodeSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(LwM2mNode.class, new LwM2mNodeDeserializer());
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        this.gson = gsonBuilder.create();
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
		String[] modelPaths = new String[] { "3303.xml"};
		List<ObjectModel> models = ObjectLoader.loadDefault();
        models.addAll(ObjectLoader.loadDdfResources("/models/", modelPaths));
        LwM2mModelProvider modelProvider = new VersionedModelProvider(models);
        
		LeshanServerBuilder builder = new LeshanServerBuilder();
		builder.setObjectModelProvider(modelProvider);
		
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
		ClientStore securityStore = new ClientStore();
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
			ReadResponse response = leshanServer.send(registration, new ReadRequest(3303,0,5700));
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
