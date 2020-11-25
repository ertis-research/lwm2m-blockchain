package com.ivan.tfm.MainAppBackEnd.beans;

public class AclEntry {
	private String client_name;
	private int object_id;
	private int resource_id;
	private int permission;
	
	public AclEntry(String client_name, int object_id, int resource_id, int permission) {
		this.client_name = client_name;
		this.object_id = object_id;
		this.resource_id = resource_id;
		this.permission = permission;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public int getResource_id() {
		return resource_id;
	}

	public void setResource_id(int resource_id) {
		this.resource_id = resource_id;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
}