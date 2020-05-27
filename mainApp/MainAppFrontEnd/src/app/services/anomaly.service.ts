import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

import { Anomaly } from '../models';
import { AuthService } from "./auth.service";
import { apiUrl } from "../core";
import { headersGenerator } from "../common";

@Injectable({
  providedIn: 'root'
})
export class AnomalyService {

  url = `${apiUrl}anomalies/`;

  constructor(private http: HttpClient, private auth: AuthService) { }

  getAllAnomalies(): Observable<Anomaly[]> {
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Anomaly[]>(this.url, { headers });
  }
}
