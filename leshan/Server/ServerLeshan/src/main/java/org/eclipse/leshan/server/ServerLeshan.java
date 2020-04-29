package org.eclipse.leshan.server;

import java.io.File;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.servlet.ClientServlet;
import org.eclipse.leshan.server.servlet.EventServlet;
import org.eclipse.leshan.server.servlet.SecurityServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLeshan {

	private static final Logger LOG = LoggerFactory.getLogger(LeshanServer.class);

	public static void main(String[] args) {

		boolean supportDeprecatedCiphers = false;

		String localAddress= "0.0.0.0";
		int localPort = 5783;
		String secureLocalAddress= "0.0.0.0";
		int secureLocalPort = 5784;

		// Prepare LWM2M server
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

		// Create and start LWM2M server
		LeshanServer lwServer = builder.build();

		// Now prepare Jetty
		int webPort = 8081;
		InetSocketAddress jettyAddr = new InetSocketAddress(webPort);

		Server server = new Server(jettyAddr);
		WebAppContext root = new WebAppContext();
		root.setContextPath("/");
		root.setResourceBase(LeshanServer.class.getClassLoader().getResource("webapp").toExternalForm());
		root.setParentLoaderPriority(true);
		server.setHandler(root);

		// Create Servlet
		EventServlet eventServlet = new EventServlet(lwServer, lwServer.getSecuredAddress().getPort());
		ServletHolder eventServletHolder = new ServletHolder(eventServlet);
		root.addServlet(eventServletHolder, "/event/*");

		ServletHolder clientServletHolder = new ServletHolder(new ClientServlet(lwServer));
		root.addServlet(clientServletHolder, "/api/clients/*");

		ServletHolder securityServletHolder = new ServletHolder(new SecurityServlet(securityStore));
		root.addServlet(securityServletHolder, "/api/security/*");

		// Start Jetty & Leshan
		lwServer.start();
		try {
			server.start();
			LOG.info("Web server started at {}.", server.getURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
