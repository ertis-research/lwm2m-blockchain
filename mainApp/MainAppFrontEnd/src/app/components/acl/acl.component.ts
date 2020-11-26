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

  entries: AclEntry[] = [];

  username: string;
  client_name: string;
  object_id: number;
  resource_id: number;
  permission: number;

  zeroEntriesMessage: string;
  listCardHeader: string;

  permissions: Permission[] = [
    { label: "None", value: 0 },
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
    if(this.client_name === undefined || this.client_name === "") {
      this.aclService.getUserEntries(this.username)
        .subscribe(data => {
          this.entries = data;
          this.handleMessageDisplay();
          this.listCardHeader = `'${this.username}' permissions`;
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
        this.entries = data;
        this.handleMessageDisplay();
        this.listCardHeader = `'${this.username}' permissions on client '${this.client_name}'`;
      }, error => {
        this.errors.handleError(error);
      });
  }

  updateEntryByModal() {
    const entry: AclEntry = {
      client_name: this.client_name,
      object_id: this.object_id,
      resource_id: this.resource_id,
      permission: this.permission,
    };
    this.updateEntry(entry);
  }

  updateEntry(entry: AclEntry) {
    this.aclService.updateEntry(this.username, entry)
      .subscribe(data => {
        console.log(data);
        document.getElementById("closeModalBtn").click();
        window.location.reload();
      }, error => {
        this.errors.handleError(error);
      });
  }

  private handleMessageDisplay() {
    if(this.entries.length === 0) {
      this.zeroEntriesMessage = `No entries found in ACL for '${this.username}'`;
      this.zeroEntriesMessage = this.client_name !== undefined && this.client_name !== "" ?
        this.zeroEntriesMessage + ` on client '${this.client_name}'` : this.zeroEntriesMessage;
    } else {
      this.zeroEntriesMessage = undefined;
    }
  }

  checkEmptyClientName(): boolean {
    return this.client_name === undefined || this.client_name === "";
  }

}

interface Permission {
  label: string;
  value: number;
}