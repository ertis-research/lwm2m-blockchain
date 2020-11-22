import { Component, OnInit } from '@angular/core';

import { Anomaly } from '../../models/Anomaly';
import { AnomalyService } from '../../services/anomaly.service';
import { timestampToDate } from "../../common";
import { ErrorService } from 'src/app/services/error.service';
import { AuthService } from 'src/app/services/auth.service';
import { EthereumAccount } from '../../models/EthereumAccount';

@Component({
  selector: 'app-anomaly',
  templateUrl: './anomaly.component.html',
  styles: []
})
export class AnomalyComponent implements OnInit {

  anomalies: Anomaly[];

  ethereumAccount:EthereumAccount={
    address: "",
    permission: true
  }
  
  constructor(private service: AnomalyService, private errors: ErrorService, private auth: AuthService) { }

  ngOnInit(): void {
    this.getAllAnomalies();
  }

  getAllAnomalies() {
    this.service.getAllAnomalies()
      .subscribe(data => {
        this.anomalies = data;
      }, error => {
        this.errors.handleError(error)
      });
  }

  tsToDate(timestamp: number) {
    if (timestamp == null) return null;
    return timestampToDate(timestamp);
  }

  isAdmin(): boolean {
    if(this.auth.getRole() === 1) {
      return true;
    }
    return false;
  }

  setPermission() {
    const response = confirm(`Are you sure you want to set the permission of the ethereum account ${this.ethereumAccount.address}?`);
    if(response === true) {
      this.service.setPermission(this.ethereumAccount)
      .subscribe(data => {
        alert("Ethereum account permission modified");
      }, error => {
        this.errors.handleError(error)
      });
    }else{
      console.log("Operation cancelled");
    } 
  }
}
