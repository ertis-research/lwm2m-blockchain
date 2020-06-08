import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from 'src/app/services/user.service';
import { ErrorService } from 'src/app/services/error.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styles: []
})
export class UserComponent implements OnInit {

  users: User[];

  username: string;
  email: string;
  password: string;
  role: number;

  roles: Role[] = [
    { label: "Admin", value: 1 },
    { label: "Advanced", value: 2 },
    { label: "Basic", value: 3},
  ];
  
  constructor(private userService: UserService, private errors: ErrorService) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers()
      .subscribe(data => {
        this.users = data;
      }, error => {
        this.errors.handleError(error)
      });
  }

  addUser() {
    const response = confirm(`Are you sure you want to add user ${this.username}?`);
    if(response === true) {
      const user: User = {
        username: this.username,
        email: this.email,
        password: this.password,
        role: this.role
      };
      this.userService.addUser(user)
        .subscribe(data => {
          this.getAllUsers();
        }, error => {
          this.errors.handleError(error)
        });
    }else{
      console.log("Operation cancelled");
    }
    
  }

  updateUser(user: User) {
    this.userService.updateUser(user)
      .subscribe(data => {
        this.getAllUsers();
      }, error => {
        this.errors.handleError(error)
      });
  }
}

interface Role {
  label: string;
  value: number;
}