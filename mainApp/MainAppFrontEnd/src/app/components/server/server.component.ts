import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { SecurityInfoServer } from '../../models/SecurityInfoServer';

@Component({
  selector: 'app-server',
  templateUrl: './server.component.html',
  styleUrls: ['./server.component.css']
})
export class ServerComponent implements OnInit {

  securityInfosServer: SecurityInfoServer[];

  constructor(private service: ApiService) {
    this.connect();
  }

  ngOnInit(): void {
    this.service.getConnectedClients()
      .subscribe(data => {
        this.securityInfosServer = data;
      })
  }


  connect(): void {
    let source = new EventSource('http://localhost:8081/event');
    
    source.addEventListener('REGISTRATION', message => {
      this.registerCallback(message);
    });

    source.addEventListener('DEREGISTRATION', message => {
      this.deregisterCallback(message);
    });


  }

  getClientIdx = function (client) {
    for (var i = 0; i < this.securityInfosServer.length; i++) {
      if (this.securityInfosServer[i].registrationId == client.registrationId) {
        return i;
      }
    }
    return -1;
  };

  registerCallback = function (msg) {
    console.log(msg);
    console.log(msg.data);
    var client = JSON.parse(msg.data);
    this.securityInfosServer.push(client);
  };


  deregisterCallback = function (msg) {
    let clientIdx = this.getClientIdx(JSON.parse(msg.data));
    if (clientIdx >= 0) {
      this.securityInfosServer.splice(clientIdx, 1);
    }
  }

}
