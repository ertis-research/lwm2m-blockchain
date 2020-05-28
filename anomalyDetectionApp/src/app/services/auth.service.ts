import { Injectable } from '@angular/core';
import { Credentials } from '../models/Credentials';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userCredentialsServer: Credentials;
  private userCredentialsAnomaly: Credentials;

  constructor(private router: Router) { 
    this.userCredentialsServer = undefined;
    this.userCredentialsAnomaly = undefined;
  }

  public setUserCredentialsServer(credentials: Credentials) {
    this.userCredentialsServer = credentials;
  }

  public setUserCredentialsAnomaly(credentials: Credentials) {
    this.userCredentialsAnomaly = credentials;
  }

  public logout() {
    this.userCredentialsServer = undefined;
    this.userCredentialsAnomaly = undefined;
    this.router.navigate(['/login']);
  }

  public isAuthenticated(): boolean {
    return this.userCredentialsServer !== undefined && this.userCredentialsAnomaly !== undefined;
  }

  public getUsername(): string {
    if(this.userCredentialsServer.username !== this.userCredentialsAnomaly.username){
      this.logout();
      return "";
    }
    return this.userCredentialsServer.username;
  }

}
