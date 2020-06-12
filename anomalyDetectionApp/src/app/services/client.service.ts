import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../models';
import { Observable } from 'rxjs';
import { headersGenerator } from '../common';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  url: string;

  constructor(private http:HttpClient, private auth: AuthService) {
    this.url = this.auth.getUrlServer() + "/api/server/";
  }

  getAllClients(): Observable<Client[]>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Client[]>(this.url,{ headers });
  }

  getValue(endpoint:string): Observable<Client>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Client>(this.url+endpoint,{ headers });
  }
}