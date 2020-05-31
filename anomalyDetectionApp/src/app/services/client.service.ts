import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../models';
import { Observable } from 'rxjs';
import { headersGenerator } from '../common';
import { AuthService } from './auth.service';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  url = this.cookies.get("urlServer");

  constructor(private http:HttpClient, private auth: AuthService, private cookies: CookieService) { }

  getAllClients(): Observable<Client[]>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Client[]>(this.url,{ headers });
  }

  getValue(endpoint:string): Observable<Client>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Client>(this.url+endpoint,{ headers });
  }

  setUrl(url:string){
    this.url = url+"/api/server/";
    this.cookies.set("urlServer", this.url);
  }
}