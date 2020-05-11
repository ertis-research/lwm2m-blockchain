import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Anomaly } from '../models/Anomaly';

@Injectable({
  providedIn: 'root'
})
export class AnomalyService {

  url = "http://localhost:8080/api/anomaly/";

  constructor(private http:HttpClient) { }

  getAllAnomalies(){
    return this.http.get<Anomaly[]>(this.url+"anomalies");
  }
}
