import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Router } from '@angular/router';

import { Credentials } from "../models/Credentials";
import { AuthService } from "../services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = "http://localhost:8080/api/login/";

  constructor(
    private http: HttpClient,
    private router: Router,
    private auth: AuthService
  ) { }

  validateLogin(wildcard: string, password: string) {
    //http post
    //check response
    //if login ok, set credentials
    this.auth.setUserCredentials("trillo@uma.es", "JTrillo", "1234567890abcdef");
    this.router.navigate(['/anomalies']);
  }
}
