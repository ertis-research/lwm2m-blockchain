import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServerBuilder;

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
	}
}
