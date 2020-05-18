import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
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
  
  constructor(private service: UserService) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.service.getAllUsers()
      .subscribe(data => {
        this.users = data;
      });
  }

  addUser() {
    const user: User = {
      username: this.username,
      email: this.email,
      password: this.password,
      role: this.role
    }
    console.log(user);
    /*this.service.addNewUser(user)
      .subscribe(data => {
        this.getAllUsers();
      });*/
  }

  deleteUser(user: User) {
    this.service.deleteUser(user.username)
      .subscribe(data => {
        this.getAllUsers();
      })
  }
}

interface Role {
  label: string;
  value: number;
}