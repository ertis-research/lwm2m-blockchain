import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/Client';
import { ErrorService } from 'src/app/services/error.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styles: ['.headers { text-align:center; }']
})
export class ClientComponent implements OnInit {

  clients: Client[];
  clientForm: Client = {
    endpoint: "",
    url_bs: "",
    id_bs: "",
    key_bs: "",
    url_s: "",
    id_s: "",
    key_s: ""
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
    const response = confirm(`Are you sure you want to create endpoint '${this.clientForm.endpoint}'?`);
    if(response === true) {
      this.service.addClient(this.clientForm)
        .subscribe(data => {
          this.getAllClients();
        }, error => {
          this.errors.handleError(error)
        });
    }else{
      console.log("Operation cancelled");
    }
  }

  deleteClient(client: Client) {
    const response = confirm(`Are you sure you want to delete endpoint '${client.endpoint}'?`);
    if(response === true) {
      this.service.deleteClient(client.endpoint)
        .subscribe(data => {
          this.getAllClients();
        }, error => {
          this.errors.handleError(error)
        });
    }else{
      console.log("Operation cancelled")
    }
  }

  checkFormFulfilled(form: any): boolean {
    const endpoint_fc: FormControl = form.controls['endpoint'];
    const url_bs_fc: FormControl = form.controls['url_bs'];
    const id_bs_fc: FormControl = form.controls['id_bs'];
    const key_bs_fc: FormControl = form.controls['key_bs'];
    const url_s_fc: FormControl = form.controls['url_s'];
    const id_s_fc: FormControl = form.controls['id_s'];
    const key_s_fc: FormControl = form.controls['key_s'];

    if(this.checkFieldFulfilled(endpoint_fc) &&
      this.checkFieldFulfilled(url_bs_fc) && this.checkFieldFulfilled(id_bs_fc) &&
      this.checkFieldFulfilled(key_bs_fc) && this.checkFieldFulfilled(url_s_fc) &&
      this.checkFieldFulfilled(id_s_fc) && this.checkFieldFulfilled(key_s_fc)) {
      return true;
    }
    return false;
  }

  private checkFieldFulfilled(control: FormControl): boolean {
    if(control === undefined) {
      return true;
    }
    if(control.value !== undefined && control.value !== null && control.value !== ""){
      return true;
    }else{
      return false;
    }
  }

}