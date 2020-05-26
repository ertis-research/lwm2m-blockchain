import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServer;
import org.eclipse.leshan.server.californium.bootstrap.LeshanBootstrapServerBuilder;

public class BootstrapServerLeshan {

	public static void main(String[] args) {
		// Variables
		boolean supportDeprecatedCiphers = false;
		
		String secureLocalAddress = "0.0.0.0";
		int secureLocalPort = 5684;
		
        // Storage configuration
        LeshanBootstrapServerBuilder builder = new LeshanBootstrapServerBuilder();
        BlockchainBootstrapStore bsStore = new BlockchainBootstrapStore();
        builder.setConfigStore(bsStore);
        builder.setSecurityStore(new BootstrapConfigSecurityStore(bsStore));
        
        // Address and port settings
        builder.disableUnsecuredEndpoint();
        builder.setLocalSecureAddress(secureLocalAddress, secureLocalPort);
        
        // DTLS config
        DtlsConnectorConfig.Builder dtlsConfig = new DtlsConnectorConfig.Builder();
        dtlsConfig.setRecommendedCipherSuitesOnly(!supportDeprecatedCiphers);
        builder.setDtlsConfig(dtlsConfig);
        
        // Start server
        LeshanBootstrapServer bsServer = builder.build();
        bsServer.start();
	}
}

