import { Component } from '@angular/core';
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
export class LoginComponent{

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
