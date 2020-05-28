import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../models/Client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  
  urlServer:string;

  constructor(private http:HttpClient) { }

  getAllClients(){
    return this.http.get<Client[]>(this.urlServer);
  }

  getValue(endpoint:string){
    return this.http.get<Client>(this.urlServer+endpoint);
  }

  setUrl(url:string){
    this.urlServer = url+"/api/server/";
  }
}