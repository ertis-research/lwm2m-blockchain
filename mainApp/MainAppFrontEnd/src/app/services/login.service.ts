import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

import { Credentials, User } from "../models";
import { baseUrl } from "../core";
import { headersGenerator } from "../common";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = `${baseUrl}login/`;

  constructor(
    private http: HttpClient,
  ) { }

  validateLogin(wildcard: string, password: string): Observable<Credentials>{
    const user_aux: User = {
      username: wildcard,
      email: wildcard,
      password,
      role: 0,
    };
    const headers = headersGenerator(true, false);
    return this.http.post<Credentials>(this.url, user_aux, { headers });
  }
}
