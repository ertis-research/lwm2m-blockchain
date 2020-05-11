package com.ivan.tfm.MainAppBackEnd.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ivan.tfm.MainAppBackEnd.beans.Anomaly;

@Service
public class AnomalyService {

	List<Anomaly> anomalies;
	
	public AnomalyService() {
		
		
		anomalies = new ArrayList<Anomaly>();
		Anomaly anomaly1 = new Anomaly(1580122988000L, "endpoint1", 1, 30.1);
		Anomaly anomaly2 = new Anomaly(1580782988000L, "endpoint2", 2, 30.6);
		Anomaly anomaly3 = new Anomaly(1580872958002L, "endpoint1", 4, 26.5);
		Anomaly anomaly4 = new Anomaly(new Timestamp(System.currentTimeMillis()).getTime(), "endpoint3", 1, 36.8);
		anomalies.add(anomaly1);
		anomalies.add(anomaly2);
		anomalies.add(anomaly3);
		anomalies.add(anomaly4);
	}
	
	public List<Anomaly> getAll(){
		return anomalies;
	}
	
	public void addAnomaly(Anomaly a) {
		anomalies.add(a);
	}
	
}
