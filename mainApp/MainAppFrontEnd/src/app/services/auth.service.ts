import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from '../models/Credentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userCredentials: Credentials;

  constructor(private router: Router) {
    this.userCredentials = undefined;
  }

  public setUserCredentials(email: string, username: string, token: string) {
    this.userCredentials = {
      email,
      username,
      token,
    };
  }

  public logout() {
    this.userCredentials = undefined;
    this.router.navigate(['/login']);
  }

  public isAuthenticated(): boolean {
    return this.userCredentials !== undefined;
  }

  // GETTERS
  public getEmail(): string {
    return this.userCredentials.email;
  }

  public getUsername(): string {
    return this.userCredentials.username;
  }

  public getToken(): string {
    return this.userCredentials.token;
  }
}
