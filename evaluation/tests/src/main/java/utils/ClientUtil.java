package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.leshan.SecurityMode;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerSecurity;
import org.web3j.tuples.generated.Tuple7;

public class ClientUtil {
	public static Map<String, BootstrapConfig> clientBytesToMap(Tuple7<List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>, List<byte[]>> tuple){
		Map<String, BootstrapConfig> res = new ConcurrentHashMap<>();

		List<byte[]> endpoints = tuple.component1();
		List<byte[]> urls_bs = tuple.component2();
		List<byte[]> ids_bs = tuple.component3();
		List<byte[]> keys_bs = tuple.component4();
		List<byte[]> urls_s = tuple.component5();
		List<byte[]> ids_s = tuple.component6();
		List<byte[]> keys_s = tuple.component7();

		for (int i = 0; i < tuple.component1().size(); i++) {
			String endpoint = Converter.byteToAscii(endpoints.get(i));
			String url_bs = Converter.byteToAscii(urls_bs.get(i));
			String id_bs = Converter.byteToAscii(ids_bs.get(i));
			String key_bs = Converter.byteToAscii(keys_bs.get(i));
			String url_s = Converter.byteToAscii(urls_s.get(i));
			String id_s = Converter.byteToAscii(ids_s.get(i));
			String key_s = Converter.byteToAscii(keys_s.get(i));

			BootstrapConfig bootstrapConfig = createBoostrapConfig(url_bs, id_bs, key_bs, url_s, id_s, key_s);
			res.put(endpoint, bootstrapConfig);
		}
		return res;
	}

	private static ServerSecurity createServerSecurity(String uri, boolean bootstrapServer, byte[] publicKeyOrId, byte[] secretKey) {
		ServerSecurity serverSecurity = new ServerSecurity();
		serverSecurity.uri = uri;
		serverSecurity.bootstrapServer = bootstrapServer;
		serverSecurity.securityMode = SecurityMode.PSK;
		serverSecurity.serverId = bootstrapServer ? 111 : 123;
		serverSecurity.publicKeyOrId = publicKeyOrId;
		serverSecurity.secretKey = secretKey;
		return serverSecurity;
	}

	private static ServerConfig createServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.shortId = 123;
		serverConfig.lifetime = 20;
		return serverConfig;
	}
	
	public static BootstrapConfig createBoostrapConfig(String url_bs, String id_bs, String key_bs, String url_s, String id_s, String key_s) {
		BootstrapConfig bootstrapConfig = new BootstrapConfig();
		bootstrapConfig.toDelete = Arrays.asList("/0","/1");
		ServerConfig serverConfig = createServerConfig();
		bootstrapConfig.servers.put(0, serverConfig);

		ServerSecurity bsSecurity =  createServerSecurity(url_bs, true, Converter.asciiToByte(id_bs), Converter.hexToByte(key_bs));
		ServerSecurity serverSecurity =  createServerSecurity(url_s, false, Converter.asciiToByte(id_s), Converter.hexToByte(key_s));

		bootstrapConfig.security.put(0, bsSecurity);
		bootstrapConfig.security.put(1, serverSecurity);

		return bootstrapConfig;
	}
}
