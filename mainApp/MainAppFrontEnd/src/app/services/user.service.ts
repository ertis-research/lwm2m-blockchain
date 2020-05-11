import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = "http://localhost:8080/api/user/";

  constructor(private http:HttpClient) { }

  getAllUsers(){
    return this.http.get<User[]>(this.url+"users");
  }

  addNewUser(user:User){
    return this.http.post(this.url+"add", user);
  }

  deleteUSer(username: string){
    return this.http.delete(this.url+"delete/"+username);
  }
}
