import { Component, OnInit } from '@angular/core';
import { Anomaly } from '../../models/Anomaly';
import { AnomalyService } from '../../services/anomaly.service';

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

  timestampToDate(timestamp: number) {
    var date = new Date(timestamp);
    var dateDisplay = date.toLocaleString('es-ES', {
      day: 'numeric',
      month: 'numeric',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
    return dateDisplay;
  }

}
