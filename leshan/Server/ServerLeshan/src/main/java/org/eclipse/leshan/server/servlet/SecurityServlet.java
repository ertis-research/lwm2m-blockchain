package org.eclipse.leshan.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.eclipse.leshan.server.security.SecurityInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;

	private final EditableSecurityStore sescurityStore;

	public SecurityServlet(EditableSecurityStore sescurityStore) {
		this.sescurityStore = sescurityStore;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SecurityServlet - doGet");
		// 1. Get all ClientInfo from smart contract
		List<ClientInfo> clientInfos = getAllClientInfo();

		// 2. initiate jackson mapper
		ObjectMapper mapper = new ObjectMapper();

		// 3. Set response type to JSON
		resp.setContentType("application/json");		    

		// 4. Send List<ClientInfo> as JSON to client
		mapper.writeValue(resp.getOutputStream(), clientInfos);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	// Get all ClientInfo from smart contract
	private List<ClientInfo> getAllClientInfo() {
		List<ClientInfo> clientInfos = new ArrayList<ClientInfo>();
		Collection<SecurityInfo> SecurityInfos =  sescurityStore.getAll();
		for (SecurityInfo securityInfo : SecurityInfos) {
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setEndpoint(securityInfo.getEndpoint());
			clientInfo.setId_s(securityInfo.getIdentity());
			clientInfo.setKey_s(byteToHex(securityInfo.getPreSharedKey()));
			clientInfos.add(clientInfo);
		}
		
		return clientInfos;
	}
	
	private String byteToHex(byte[] bytes) {
		String res ="";
		for (byte b : bytes) {
			String st = String.format("%02X", b);
			res+=st;
		}
		return res.trim();
	}

}
