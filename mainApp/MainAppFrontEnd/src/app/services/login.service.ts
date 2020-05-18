import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

import { Credentials, User } from "../models";
import { baseUrl } from "../core";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = `${baseUrl}login/`;

  constructor(
    private http: HttpClient,
  ) { }

  validateLogin(wildcard: string, password: string) {
    const user_aux: User = {
      username: wildcard,
      email: wildcard,
      password,
      role: 0,
    };
    return this.http.post<Credentials>(this.url, user_aux);
    
  }
}
