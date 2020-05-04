import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { BootstrapConfig } from '../../models/BootstrapConfig';

@Component({
  selector: 'app-bootstrap-server',
  templateUrl: './bootstrap-server.component.html',
  styleUrls: ['./bootstrap-server.component.css']
})
export class BootstrapServerComponent implements OnInit {

  bootstrapConfigs: BootstrapConfig[];
  bootstrapConfigForm: BootstrapConfig = {
    endpoint: "ivanEndpoint",
    url_bs: "coaps://localhost:5684",
    id_bs: "ivan",
    key_bs: "654321",
    url_s: "coaps://localhost:5784",
    id_s: "ivan",
    key_s: "123456",
  }

  constructor(private service: ApiService) { }

  ngOnInit() {
    this.getSecurityInfosBs();
  }

  getSecurityInfosBs() {
    this.service.getBootstrapConfigs()
      .subscribe(data => {
        this.bootstrapConfigs = data;
      })
  }

  addSecurityInfo() {
    this.service.addNewBootstrapConfig(this.bootstrapConfigForm)
      .subscribe(data => {
        this.getSecurityInfosBs();
      })
  }

  deleteSecurityInfo(bootstrapConfig: BootstrapConfig) {

    this.service.deleteBootstrapConfig(bootstrapConfig.endpoint)
      .subscribe(data => {
        this.getSecurityInfosBs();
      })

  }

}
