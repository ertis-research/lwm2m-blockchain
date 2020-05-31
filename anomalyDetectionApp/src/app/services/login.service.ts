import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, Credentials } from '../models';
import { headersGenerator } from '../common';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  validateLogin(wildcard: string, password: string, url: string): Observable<Credentials>{
    const user_aux: User = {
      username: wildcard,
      email: wildcard,
      password,
      role: 0,
    };
    const headers = headersGenerator(true, false);
    return this.http.post<Credentials>(url+"/login", user_aux, { headers });
  }
}