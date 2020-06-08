import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { LoginService } from '../../services/login.service';
import { Credentials } from '../../models';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: ['.invalid { color: red; font-weight: bold; }']
})
export class LoginComponent implements OnInit{

  urlServer: string;
  wildcard: string;
  password: string;
  message: string;

  constructor(
    private router: Router,
    private clientService:ClientService,
    private auth: AuthService,
    private login: LoginService
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
    this.login.validateLogin(this.wildcard,this.password, this.urlServer)
      .subscribe((response: Credentials) => {
        this.message = undefined;
        this.auth.setUserCredentials(response);
        this.clientService.setUrl(this.urlServer);
        this.router.navigate(['/clients']);
      }, error => {
        this.message = "User does not exist or password is incorrect in Leshan server";
      });  
  }
}
