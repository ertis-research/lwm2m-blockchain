import { Injectable } from '@angular/core';

import { AuthService } from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(
    private auth: AuthService,
  ) { }

  handleError(error:any) {
    if(error.status == 401) {
      this.auth.logout(true);
    }
  }
}