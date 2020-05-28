import { Injectable } from '@angular/core';
import { Anomaly } from '../models/Anomaly';
import { HttpClient } from '@angular/common/http';
import { mainServerUrl } from "../core";

@Injectable({
  providedIn: 'root'
})
export class AnomalyService {

  constructor(private http: HttpClient) { }

  url = `${mainServerUrl}api/anomalies/add`;

  addAnomaly(anomaly: Anomaly) {
    return this.http.post(this.url, anomaly);
  }

  analyzeTemperature(x: number) {
    let level = 0;
    switch (true) {
      case x > 50:
          level = 3;
          break;
      case x > 45:
          level = 2;
          break;
      case x > 40:
          level = 1;
          break;
      default:
          level = 0;
    }

    return level;
  }

}