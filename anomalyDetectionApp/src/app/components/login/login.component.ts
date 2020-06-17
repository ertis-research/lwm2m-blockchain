import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { Credentials } from '../../models';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
    '.invalid { color: red; font-weight: bold; }',
    '.radio-div { display: flex; justify-content: center; }',
  ]
})
export class LoginComponent implements OnInit{

  urlServer: string;
  username: string;
  email: string;
  password: string;
  message: string;
  option: number;

  constructor(
    private router: Router,
    private auth: AuthService,
    private login: LoginService,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(data => {
      if(data.expired){
        alert('Your token has expired. You must log in again.');
      }
    });
    this.option = 1;
  }

  validateLogin() {
    let wildcard = "";
    if(this.option === 1) {
      wildcard = this.username;
    }else{
      wildcard = this.email;
    }
    this.login.validateLogin(wildcard,this.password, this.urlServer)
      .subscribe((response: Credentials) => {
        this.message = undefined;
        this.auth.setUserCredentials(response);
        this.auth.setUrlServer(this.urlServer);
        this.router.navigate(['/clients']);
      }, error => {
        this.message = "User does not exist or password is incorrect in Leshan server";
      });  
  }
}
