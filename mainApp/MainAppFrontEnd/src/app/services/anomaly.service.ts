import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Anomaly } from '../models';
import { baseUrl } from "../core";

@Injectable({
  providedIn: 'root'
})
export class AnomalyService {

  url = `${baseUrl}anomalies/`;

  constructor(private http:HttpClient) { }

  getAllAnomalies(){
    return this.http.get<Anomaly[]>(this.url);
  }
}
