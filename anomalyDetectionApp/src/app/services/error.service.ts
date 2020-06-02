import { Injectable } from '@angular/core';
import { Router } from "@angular/router";

import { AuthService } from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(
    private router: Router,
    private auth: AuthService,
  ) { }

  handleError(error:any) {
    if(error.status == 401) {
      this.auth.logout();
      this.router.navigate(['/login'], { state: { tokenExpired: true } });
    }
  }
}