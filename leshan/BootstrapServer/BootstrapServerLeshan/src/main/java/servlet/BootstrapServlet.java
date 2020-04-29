package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.leshan.SecurityMode;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerConfig;
import org.eclipse.leshan.server.bootstrap.BootstrapConfig.ServerSecurity;
import org.eclipse.leshan.server.bootstrap.EditableBootstrapConfigStore;
import org.eclipse.leshan.server.bootstrap.InvalidConfigurationException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BootstrapServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final EditableBootstrapConfigStore bsStore;

	public BootstrapServlet(EditableBootstrapConfigStore bsStore) {
		//System.out.println("BootstrapServlet - constructor");
		this.bsStore = bsStore;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("doPost");

		// 1. Get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String json = "";
		if(br != null){
			json = br.readLine();
			System.out.println(json);
		}

		// 2. Initiate jackson mapper
		ObjectMapper mapper = new ObjectMapper();

		// 3. Convert received JSON to ClientInfo
		ClientInfo  clientInfoPost = mapper.readValue(json, ClientInfo.class);

		// 4. Create BootstrapConfig object
		BootstrapConfig bootstrapConfig = new BootstrapConfig();
		bootstrapConfig.toDelete = Arrays.asList("/0","/1");
		ServerConfig serverConfig = createServerConfig();
		bootstrapConfig.servers.put(0, serverConfig);
		bootstrapConfig.security.put(0, createServerSecurity(clientInfoPost.getUrl_bs(), true, asciiToByte32(clientInfoPost.getId_bs()), asciiToByte32(clientInfoPost.getKey_bs())));
		bootstrapConfig.security.put(1, createServerSecurity(clientInfoPost.getUrl_s(), false, asciiToByte32(clientInfoPost.getId_s()), asciiToByte32(clientInfoPost.getKey_s())));

		// 5. Add this ClientInfo to the smart contract
		try {
			bsStore.add(clientInfoPost.getEndpoint(), bootstrapConfig);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

		// 6. Get all ClientInfo from smart contract
		List<ClientInfo> clientInfos = getAllClientInfo();

		// 7. Set response type to JSON
		response.setContentType("application/json");
		setAccessControlHeaders(response);
		
		// 8. Send List<ClientInfo> as JSON to client
		mapper.writeValue(response.getOutputStream(), clientInfos);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("doGet");

		// 1. Get all ClientInfo from smart contract
		List<ClientInfo> clientInfos = getAllClientInfo();

		// 2. initiate jackson mapper
		ObjectMapper mapper = new ObjectMapper();

		// 3. Set response type to JSON
		response.setContentType("application/json");
		setAccessControlHeaders(response);

		// 4. Send List<ClientInfo> as JSON to client
		mapper.writeValue(response.getOutputStream(), clientInfos);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doDelete");

		// 1. Get endpoint name 
		if (request.getPathInfo() == null) return; // we need the endpoint in the URL
		String[] path = StringUtils.split(request.getPathInfo(), '/');
		if (path.length != 1) return; // endpoint name should be specified in the URL, nothing more
		String endpoint = path[0];

		// 2. Remove client with that endpoint 
		if (bsStore.remove(endpoint) != null) {

			// 3. Get all ClientInfo from smart contract
			List<ClientInfo> clientInfos = getAllClientInfo();

			// 4. initiate jackson mapper
			ObjectMapper mapper = new ObjectMapper();

			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send List<ClientInfo> as JSON to client
			mapper.writeValue(response.getOutputStream(), clientInfos);
		} else {
			System.out.println("no config for " + endpoint);
		}
	}

	// Convert byte to hex
	private String byteToHex(byte[] bytes) {
		String res ="";
		for (byte b : bytes) {
			String st = String.format("%02X", b);
			res+=st;
		}
		return res.trim();
	}

	// Convert byte to ascii
	private String byteToAscii(byte[] bytes) {
		String res="";
		try {
			res = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return res.trim();
	}

	// Create ServerConfig object. This is invariable
	private ServerConfig createServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.shortId = 123;
		serverConfig.lifetime = 20;
		return serverConfig;
	}

	// Create ServerSecurity object
	private ServerSecurity createServerSecurity(String uri, boolean bootstrapServer, byte[] publicKeyOrId, byte[] secretKey) {
		ServerSecurity serverSecurity = new ServerSecurity();
		serverSecurity.uri = uri;
		serverSecurity.bootstrapServer = bootstrapServer;
		serverSecurity.securityMode = SecurityMode.PSK;
		serverSecurity.serverId = bootstrapServer ? 111 : 123;
		serverSecurity.publicKeyOrId = publicKeyOrId;
		serverSecurity.secretKey = secretKey;
		return serverSecurity;
	}

	// Get all ClientInfo from smart contract
	private List<ClientInfo> getAllClientInfo() {
		List<ClientInfo> clientInfos = new LinkedList<ClientInfo>();
		Map<String, BootstrapConfig> mapClientinfo =  bsStore.getAll();
		for (Map.Entry<String, BootstrapConfig> config : mapClientinfo.entrySet()) {
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setEndpoint(config.getKey());
			clientInfo.setUrl_bs(config.getValue().security.get(0).uri);
			clientInfo.setId_bs(byteToAscii(config.getValue().security.get(0).publicKeyOrId));
			clientInfo.setKey_bs(byteToHex(config.getValue().security.get(0).secretKey));
			clientInfo.setUrl_s(config.getValue().security.get(1).uri);
			clientInfo.setId_s(byteToAscii(config.getValue().security.get(1).publicKeyOrId));
			clientInfo.setKey_s(byteToHex(config.getValue().security.get(1).secretKey));
			clientInfos.add(clientInfo);
		}
		return clientInfos;
	}
	
	private byte[] asciiToByte32(String ascii){
		byte[] bytearray = new byte[32];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};
	

	// Convert ascii to byte
	private byte[] asciiToByte(String ascii){
		byte[] bytearray = new byte[ascii.length()];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};

	// Conver hex to byte
	private byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[32];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "*");
	      resp.setHeader("Access-Control-Allow-Credentials", "true");
	      resp.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
	      resp.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	  }
}

