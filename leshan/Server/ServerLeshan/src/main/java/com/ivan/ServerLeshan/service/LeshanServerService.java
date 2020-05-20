package com.ivan.ServerLeshan.service;

import java.io.File;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.springframework.stereotype.Service;

@Service
public class LeshanServerService {

	boolean supportDeprecatedCiphers = false;

	String localAddress= "0.0.0.0";
	int localPort = 5783;
	String secureLocalAddress= "0.0.0.0";
	int secureLocalPort = 5784;

	LeshanServer lwServer;

	public LeshanServerService() {
		lwServer = configureLeshanServer().build();
		lwServer.start();
	}

	
	public LeshanServer getLeshanServer() {
		return lwServer;
	}
	
	public void startLeshanServer() {
		lwServer.start();
	}
	
	private LeshanServerBuilder configureLeshanServer() {
		LeshanServerBuilder builder = new LeshanServerBuilder();
		builder.setLocalAddress(localAddress, localPort);
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

}
