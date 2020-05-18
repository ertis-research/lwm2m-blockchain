import { Component, OnInit } from '@angular/core';

import { Anomaly } from '../../models/Anomaly';
import { AnomalyService } from '../../services/anomaly.service';
import { timestampToDate } from "../../common";

@Component({
  selector: 'app-anomaly',
  templateUrl: './anomaly.component.html',
  styleUrls: ['./anomaly.component.css']
})
export class AnomalyComponent implements OnInit {

  anomalies: Anomaly[];

  constructor(private service: AnomalyService) { }

  ngOnInit(): void {
    this.getAllAnomalies();
  }

  getAllAnomalies() {
    this.service.getAllAnomalies()
      .subscribe(data => {
        this.anomalies = data;
      });
  }

  tsToDate(timestamp: number) {
    timestampToDate(timestamp);
  }

}
