import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from "ngx-cookie-service";
import { Credentials } from '../models/Credentials';
import { baseCookie } from "../core";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private cookies: CookieService) { }

  public setUserCredentials(credentials: Credentials) {
    this.cookies.set(`${baseCookie}username`, credentials.username);
    this.cookies.set(`${baseCookie}email`, credentials.email);
    this.cookies.set(`${baseCookie}role`, String(credentials.role));
    this.cookies.set(`${baseCookie}token`, credentials.token);
  }

  public logout(tokenExpired: boolean) {
    this.cookies.delete(`${baseCookie}username`);
    this.cookies.delete(`${baseCookie}email`);
    this.cookies.delete(`${baseCookie}role`);
    this.cookies.delete(`${baseCookie}token`);
    if(tokenExpired) {
      this.router.navigate(['/login', true]);
    } else {
      this.router.navigate(['/login']);
    }  
  }

  public isAuthenticated(): boolean {
    return this.cookies.check(`${baseCookie}username`);
  }

  // GETTERS
  public getEmail(): string {
    return this.cookies.get(`${baseCookie}email`);
  }

  public getUsername(): string {
    return this.cookies.get(`${baseCookie}username`);
  }

  public getRole(): number {
    return this.isAuthenticated()
      ? +this.cookies.get(`${baseCookie}role`)
      : 0;
  }

  public getToken(): string {
    return this.cookies.get(`${baseCookie}token`);
  }
}
