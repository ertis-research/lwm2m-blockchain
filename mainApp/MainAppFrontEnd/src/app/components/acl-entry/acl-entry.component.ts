import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AclEntry } from "src/app/models";

@Component({
  selector: 'acl-entry, [acl-entry]',
  templateUrl: './acl-entry.component.html',
  styles: [
  ]
})
export class AclEntryComponent implements OnInit {
  @Input('username') username: string;
  @Input('entry') entry: AclEntry;
  @Output('onUpdate') onUpdate: EventEmitter<AclEntry> = new EventEmitter<AclEntry>();
  permission: number;

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

  constructor() { }

  ngOnInit(): void {
    this.permission = this.entry.permission;
  }

  updateEntry() {
    const response = confirm(`Are you sure you want to change '${this.username}' permission on client '${this.entry.client_name}'`)
    if(response === true) {
      this.entry.permission = this.permission;
      this.onUpdate.emit(this.entry);
    } else {
      this.permission = this.entry.permission;
      console.log("Operation cancelled");
    }
  }

}

interface Permission {
  label: string;
  value: number;
}