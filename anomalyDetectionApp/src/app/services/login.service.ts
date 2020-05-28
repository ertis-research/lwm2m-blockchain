import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Credentials } from '../models/Credentials';
import { User } from '../models/User';
import { mainServerUrl } from '../core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  mainServerUrl = `${mainServerUrl}login/`;

  constructor(private http: HttpClient) { }

  validateLoginServer(wildcard: string, password: string, url: string) {
    const user_aux: User = {
      username: wildcard,
      email: wildcard,
      password,
      role: 0,
    };
    return this.http.post<Credentials>(url+"/api/login/", user_aux);
  }

  validateLoginMainServer(wildcard: string, password: string) {
    const user_aux: User = {
      username: wildcard,
      email: wildcard,
      password,
      role: 0,
    };
    return this.http.post<Credentials>(this.mainServerUrl, user_aux);
  }

}