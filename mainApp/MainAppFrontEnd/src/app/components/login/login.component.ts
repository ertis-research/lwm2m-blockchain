import { Component } from '@angular/core';
import { LoginService } from "../../services/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
  ]
})
export class LoginComponent {

  wildcard: string;
  password: string;

  constructor(private login: LoginService) { }

  validateLogin() {
    this.login.validateLogin(this.wildcard, this.password);
  }
}
