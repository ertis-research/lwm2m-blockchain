import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from '../../services/client.service';
import { LoginService } from '../../services/login.service';
import { Credentials } from '../../models/Credentials';
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
    // validation in server
    this.login.validateLoginServer(this.wildcard,this.password, this.urlServer)
      .subscribe((response: Credentials) => {
        this.message = undefined;
        this.auth.setUserCredentialsServer(response);

        // validation in  mainServer
        this.login.validateLoginMainServer(this.wildcard,this.password)
          .subscribe((response: Credentials) => {
            this.message = undefined;
            this.auth.setUserCredentialsAnomaly(response);
            this.clientService.setUrl(this.urlServer);
            this.router.navigate(['/clients']);
          }, error => {
            this.message = "User does not exist or password is incorrect in mainServer";
          });
        
      }, error => {
        this.message = "User does not exist or password is incorrect in Leshan server";
      });  
  }
}
