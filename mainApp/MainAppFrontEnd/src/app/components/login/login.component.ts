import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from "../../services/login.service";
import { AuthService } from '../../services/auth.service';
import { Credentials } from '../../models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: ['.invalid { color: red; font-weight: bold; }']
})
export class LoginComponent implements OnInit {

  wildcard: string;
  password: string;
  message: string;

  constructor(
    private login: LoginService,
    private auth: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    if(this.router.getCurrentNavigation() != null) {
      const tokenExpired = this.router.getCurrentNavigation().extras.state.tokenExpired;
      if(tokenExpired != undefined && tokenExpired != null && tokenExpired) {
        alert('Your token has expired. You must log in again.')
      }
    }
  }

  validateLogin() {
    this.login.validateLogin(this.wildcard, this.password)
      .subscribe((response: Credentials) => {
        this.message = undefined;
        this.auth.setUserCredentials(response);
        this.router.navigate(['/anomalies']);
      }, error => {
        this.message = "User does not exist or password is incorrect";
      });
  }
}
