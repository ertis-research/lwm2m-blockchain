import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BootstrapConfig } from '../models/BootstrapConfig';
import { SecurityInfoServer } from '../models/SecurityInfoServer';

@Injectable({
  providedIn: 'root'
})

export class ApiService {

  constructor(private http:HttpClient) {
    console.log("service listo");
  }

  url_bs = "http://localhost:8080/api/bootstrap/";
   
  getBootstrapConfigs(){
    return this.http.get<BootstrapConfig[]>(this.url_bs+"clients");
  }

  getConnectedClients(){
    return this.http.get<SecurityInfoServer[]>('http://localhost:8081/api/clients');
  }

  addNewBootstrapConfig(bootstrapConfig:BootstrapConfig){
    return this.http.post(this.url_bs+"addClient", bootstrapConfig);
  }

  deleteBootstrapConfig(endpoint: String){
    return this.http.delete(this.url_bs+"removeClient/"+endpoint);
  }

}
