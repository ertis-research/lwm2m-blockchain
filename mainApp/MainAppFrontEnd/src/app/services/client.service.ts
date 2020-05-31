import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

import { Client } from '../models';
import { AuthService } from "./auth.service";
import { apiUrl } from "../core";
import { headersGenerator } from '../common';

@Injectable({
  providedIn: 'root'
})

export class ClientService {

  constructor(private http: HttpClient, private auth: AuthService) {
  }

  url = `${apiUrl}clients/`;
   
  getAllClients(): Observable<Client[]>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<Client[]>(this.url, { headers });
  }

  addClient(client:Client): Observable<any>{
    const headers = headersGenerator(true, true, this.auth.getToken());
    return this.http.post(this.url+"add", client, { headers });
  }

  deleteClient(endpoint: string): Observable<any>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.delete(this.url+"remove/"+endpoint, { headers });
  }

}
