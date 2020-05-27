import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

import { User } from '../models';
import { AuthService } from "./auth.service";
import { apiUrl } from "../core";
import { headersGenerator } from "../common";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = `${apiUrl}users/`;

  constructor(private http: HttpClient, private auth: AuthService) { }

  getAllUsers(): Observable<User[]>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<User[]>(this.url, { headers });
  }

  addNewUser(user:User): Observable<any>{
    const headers = headersGenerator(true, true, this.auth.getToken());
    return this.http.post(this.url+"add", user, { headers });
  }

  deleteUser(username: string): Observable<any>{
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.delete(this.url+"delete/"+username, { headers });
  }
}
