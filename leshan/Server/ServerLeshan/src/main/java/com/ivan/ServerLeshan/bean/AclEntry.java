package com.ivan.ServerLeshan.bean;

import java.util.List;

public class AclEntry {
	private String user;
	private String endpoint;
	private int objectId;
	private List<Integer> resourceId;
	private int permission;
	
	public AclEntry(String user, String endpoint, int objectId, List<Integer> resourceId, int permission) {
		this.user = user;
		this.endpoint = endpoint;
		this.objectId = objectId;
		this.resourceId = resourceId;
		this.permission = permission;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public List<Integer> getResourceId() {
		return resourceId;
	}

	public void setResourceId(List<Integer> resourceId) {
		this.resourceId = resourceId;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
}
