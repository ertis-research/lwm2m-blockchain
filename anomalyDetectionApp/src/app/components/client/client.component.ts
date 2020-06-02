import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client, Anomaly } from '../../models';
import { AnomalyService } from '../../services/anomaly.service';
import { timestampToDate } from '../../common/utils';
import { ErrorService } from '../../services/error.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styles: []
})
export class ClientComponent implements OnInit {

  clients: Client[];

  constructor(private service: ClientService, private serviceAnomaly:AnomalyService, private errors: ErrorService) { }

  ngOnInit(): void {
    this.getAllClients();
  }

  getAllClients() {
    this.service.getAllClients()
      .subscribe(data => {
        this.clients = data;
        this.clients.forEach(client => {
          client.value = null;
          client.valueTimestamp = null;
        });
      }, error => {
        this.errors.handleError(error)
      })
  }

  getValue(endpoint: string) {
    this.service.getValue(endpoint)
      .subscribe(data => {
        for (let i = 0; i < this.clients.length; i++) {
          if (this.clients[i].endpoint == endpoint) {
            this.clients[i].value = data.value;
            this.clients[i].valueTimestamp = data.valueTimestamp;
            this.clients[i].lastUpdateTimestamp = data.lastUpdateTimestamp;
          }

          let level = this.serviceAnomaly.analyzeTemperature(data.value);
          if (0 !== level) {
            var anomaly: Anomaly = {
              timestamp: data.valueTimestamp,
              endpoint: data.endpoint,
              emergencyLevel: level,
              temperature: data.value
            }
            this.serviceAnomaly.addAnomaly(anomaly)
              .subscribe(data => {
                console.log(data);
              })
          }
        }
      }, error => {
        this.errors.handleError(error)
      })
  }

  tsToDate(timestamp: number) {
    if (timestamp == null) return null;
    return timestampToDate(timestamp);
  }

}