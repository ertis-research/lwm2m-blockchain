import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../models/Client';

@Injectable({
  providedIn: 'root'
})

export class ClientService {

  constructor(private http:HttpClient) {
  }

  url_bs = "http://localhost:8080/api/client/";
   
  getAllClients(){
    return this.http.get<Client[]>(this.url_bs+"clients");
  }

  addClient(client:Client){
    return this.http.post(this.url_bs+"addClient", client);
  }

  deleteClient(endpoint: string){
    return this.http.delete(this.url_bs+"removeClient/"+endpoint);
  }

}
