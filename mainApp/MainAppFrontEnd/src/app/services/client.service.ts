import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Client } from '../models';
import { baseUrl } from "../core";

@Injectable({
  providedIn: 'root'
})

export class ClientService {

  constructor(private http:HttpClient) {
  }

  url = `${baseUrl}clients/`;
   
  getAllClients(){
    return this.http.get<Client[]>(this.url);
  }

  addClient(client:Client){
    return this.http.post(this.url+"add", client);
  }

  deleteClient(endpoint: string){
    return this.http.delete(this.url+"remove/"+endpoint);
  }

}
