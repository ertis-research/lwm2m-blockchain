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

  public setUserCredentials(credentials: Credentials) {
    this.userCredentials = credentials;
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

  public getRole(): number {
    return this.userCredentials !== undefined
      ? this.userCredentials.role
      : 0;
  }

  public getToken(): string {
    return this.userCredentials.token;
  }
}
