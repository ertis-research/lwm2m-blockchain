package com.ivan.tfm.MainAppBackEnd.beans;

public class Anomaly {

	private Long timestamp;
	private String endpoint;
	private int emergencyLevel;
	private double temperature;
	
	public Anomaly(Long timestamp, String endpoint, int emergencyLevel, double temperature) {
		this.timestamp = timestamp;
		this.endpoint = endpoint;
		this.emergencyLevel = emergencyLevel;
		this.temperature = temperature;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public int getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(int emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "Anomaly [timestamp=" + timestamp + ", endpoint=" + endpoint + ", emergencyLevel=" + emergencyLevel
				+ ", temperature=" + temperature + "]";
	}
	
}
