import { Component, OnInit } from '@angular/core';

import { Anomaly } from '../../models/Anomaly';
import { AnomalyService } from '../../services/anomaly.service';
import { timestampToDate } from "../../common";
import { ErrorService } from 'src/app/services/error.service';
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
  
  constructor(private service: AnomalyService, private errors: ErrorService,) { }

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
}
