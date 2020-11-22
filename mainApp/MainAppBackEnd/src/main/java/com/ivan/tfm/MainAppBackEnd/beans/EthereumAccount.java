package com.ivan.tfm.MainAppBackEnd.beans;

public class EthereumAccount {
	private String address;
	private boolean permission;
	
	public EthereumAccount(String address, boolean permission) {
		this.address = address;
		this.permission = permission;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isPermission() {
		return permission;
	}
	
	public void setPermission(boolean permission) {
		this.permission = permission;
	}
}