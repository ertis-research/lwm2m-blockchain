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
  userForm: User = {
    username: "newUsername",
    password: "newPassword",
    role: "basic"
  }

  roles:string[]=["admin","advanced","basic"];
  
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
    this.service.addNewUser(this.userForm)
      .subscribe(data => {
        this.getAllUsers();
      })
  }

  deleteUser(user: User) {
    this.service.deleteUSer(user.username)
      .subscribe(data => {
        this.getAllUsers();
      })
  }
}
