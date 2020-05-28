package com.ivan.ServerLeshan.bean;

public class Client {
	private String endpoint;
	private long registrationTimestamp;
	private long lastUpdateTimestamp;
	private double value;
	private long valueTimestamp;
	
	public Client(String endpoint, long registrationTimestamp, long lastUpdateTimestamp) {
		this.endpoint = endpoint;
		this.registrationTimestamp = registrationTimestamp;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public long getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	public void setRegistrationTimestamp(long registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getValueTimestamp() {
		return valueTimestamp;
	}

	public void setValueTimestamp(long valueTimestamp) {
		this.valueTimestamp = valueTimestamp;
	}	
}