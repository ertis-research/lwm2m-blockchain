import { Injectable } from '@angular/core';
import { Credentials } from '../models';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private cookies: CookieService) { }

  public setUserCredentials(credentials: Credentials) {
    this.cookies.set("username", credentials.username);
    this.cookies.set("email", credentials.email);
    this.cookies.set("role", String(credentials.role));
    this.cookies.set("token", credentials.token);
  }

  public logout() {
    this.cookies.delete("username");
    this.cookies.delete("email");
    this.cookies.delete("role");
    this.cookies.delete("token");
    this.router.navigate(['/login']);
  }

  public isAuthenticated(): boolean {
    return this.cookies.check("username");
  }

  // GETTERS
  public getEmail(): string {
    return this.cookies.get("email");
  }
  
  public getUsername(): string {
    return this.cookies.get("username");
  }
  
  public getRole(): number {
    return this.isAuthenticated()
      ? +this.cookies.get("role")
      : 0;
  }
  
  public getToken(): string {
    return this.cookies.get("token");
  }
}