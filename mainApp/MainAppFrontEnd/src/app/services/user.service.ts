import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models';
import { baseUrl } from "../core";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = `${baseUrl}users/`;

  constructor(private http:HttpClient) { }

  getAllUsers(){
    return this.http.get<User[]>(this.url);
  }

  addNewUser(user:User){
    return this.http.post(this.url+"add", user);
  }

  deleteUser(username: string){
    return this.http.delete(this.url+"delete/"+username);
  }
}
