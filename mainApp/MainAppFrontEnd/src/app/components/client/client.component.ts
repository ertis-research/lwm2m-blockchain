import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/Client';
import { ErrorService } from 'src/app/services/error.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styles: ['.headers { text-align:center; }']
})
export class ClientComponent implements OnInit {

  clients: Client[];
  clientForm: Client = {
    endpoint: "ivanEndpoint",
    url_bs: "coaps://localhost:5684",
    id_bs: "ivan",
    key_bs: "654321",
    url_s: "coaps://localhost:5784",
    id_s: "ivan",
    key_s: "123456"
  }

  constructor(private service: ClientService, private errors: ErrorService) { }

  ngOnInit() {
    this.getAllClients();
  }

  getAllClients() {
    this.service.getAllClients()
      .subscribe(data => {
        this.clients = data;
      }, error => {
        this.errors.handleError(error)
      })
  }

  addClient() {
    this.service.addClient(this.clientForm)
      .subscribe(data => {
        this.getAllClients();
      }, error => {
        this.errors.handleError(error)
      })
  }

  deleteClient(client: Client) {
    this.service.deleteClient(client.endpoint)
      .subscribe(data => {
        this.getAllClients();
      }, error => {
        this.errors.handleError(error)
      })

  }

}
