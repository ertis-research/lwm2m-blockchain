import java.net.InetSocketAddress;

import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServerBuilder;

import servlet.BootstrapServlet;

public class BootstrapServerLeshan {

	public static void main(String[] args) {
        
		boolean supportDeprecatedCiphers = false;
		String localAddress = "0.0.0.0";
		String secureLocalAddress = "0.0.0.0";
		int localPort = 5683;
		int secureLocalPort = 5684;
		
        // Prepare and start bootstrap server
        LeshanBootstrapServerBuilder builder = new LeshanBootstrapServerBuilder();
        BlockchainBootstrapStore bsStore = new BlockchainBootstrapStore();
        builder.setConfigStore(bsStore);
        builder.setSecurityStore(new BootstrapConfigSecurityStore(bsStore));
        builder.setLocalAddress(localAddress, localPort);
        builder.setLocalSecureAddress(secureLocalAddress, secureLocalPort);
        
        // Create and set DTLS Config
        DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
        dtlsConfig.setRecommendedCipherSuitesOnly(!supportDeprecatedCiphers);
        builder.setDtlsConfig(dtlsConfig);
        
        LeshanBootstrapServer bsServer = builder.build();
        bsServer.start();
        
        // Prepare and start jetty
        int webPort = 8080;
        InetSocketAddress jettyAddr;
        jettyAddr = new InetSocketAddress(webPort);
  
        Server server = new Server(jettyAddr);
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setResourceBase(BootstrapServerLeshan.class.getClassLoader().getResource("webapp").toExternalForm());
        root.setParentLoaderPriority(true);

        ServletHolder bsServletHolder = new ServletHolder(new BootstrapServlet(bsStore));
        root.addServlet(bsServletHolder, "/api/bootstrap/*");

        server.setHandler(root);

        try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
