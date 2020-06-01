import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { User } from 'src/app/models';

@Component({
  selector: 'user-entry, [user-entry]',
  templateUrl: './user-entry.component.html',
  styles: [
  ]
})
export class UserEntryComponent implements OnInit {
  @Input('user') user: User;
  @Output('onUpdate') onUpdate: EventEmitter<User> = new EventEmitter<User>();
  role: number;

  roles: Role[] = [
    { label: "Admin", value: 1 },
    { label: "Advanced", value: 2 },
    { label: "Basic", value: 3},
  ];

  constructor() { }

  ngOnInit(): void {
    this.role = this.user.role;
  }

  updateUser() {
    const response = confirm(`Are you sure you want change user ${this.user.username} role?`)
    if(response === true) {
      this.user.role = this.role;
      this.onUpdate.emit(this.user);
    }else{
      this.role = this.user.role;
    }
  }
}

interface Role {
  label: string;
  value: number;
}