import { Component } from '@angular/core';
import { AclEntry } from "../../models";
import { AclService } from 'src/app/services/acl.service';
import { ErrorService } from 'src/app/services/error.service';

@Component({
  selector: 'app-acl',
  templateUrl: './acl.component.html',
  styles: []
})
export class AclComponent {

  entries: AclEntry[];

  username: string;
  client_name: string;
  object_id: number;
  resource_id: number;
  permission: number;

  permissions: Permission[] = [
    { label: "Nothing", value: 0 },
    { label: "Read only", value: 1 },
    { label: "Write only", value: 2 },
    { label: "Read & write", value: 3 },
    { label: "Execute only", value: 4},
    { label: "Read & execute", value: 5},
    { label: "Write & execute", value: 6},
    { label: "Read, write & execute", value: 7}
  ];

  constructor(private aclService: AclService, private errors: ErrorService) { }

  getUserEntries() {
    console.log(this.client_name);
    if(this.client_name === undefined || this.client_name === "") {
      this.aclService.getUserEntries(this.username)
        .subscribe(data => {
          console.log(data);
          this.entries = data;
        }, error => {
          this.errors.handleError(error);
        });
    } else {
      this.getUserEntriesByClient();
    }
    
  }

  getUserEntriesByClient() {
    this.aclService.getUserEntriesByClient(this.username, this.client_name)
      .subscribe(data => {
        console.log(data);
        this.entries = data;
      }, error => {
        this.errors.handleError(error);
      });
  }

  updateEntry() {
    this.aclService.updateEntry
  }

}

interface Permission {
  label: string;
  value: number;
}