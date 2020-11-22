import { Injectable } from '@angular/core';
import { Anomaly } from '../models';
import { HttpClient } from '@angular/common/http';
import { addAnomalyUrl } from "../core";
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { headersGenerator } from '../common/headers';

@Injectable({
  providedIn: 'root'
})
export class AnomalyService {

  privateKey:string = ""; //FILL - private key of Ethereum account
  
  constructor(private http: HttpClient, private auth: AuthService) {
  }

  url = `${addAnomalyUrl}`;

  addAnomaly(anomaly: Anomaly): Observable<any>{
    const headers = headersGenerator(true, true, this.auth.getToken());
    anomaly.privateKey = this.privateKey;
    return this.http.post(this.url, anomaly, { headers });
  }

  analyzeTemperature(x: number): number {
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