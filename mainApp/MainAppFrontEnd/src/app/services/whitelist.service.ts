import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

import { EthereumAccount } from "../models";
import { AuthService } from "./auth.service";
import { apiUrl } from "../core";
import { headersGenerator } from "../common";

@Injectable({
  providedIn: 'root'
})
export class WhitelistService {

  url = `${apiUrl}whitelist/`;

  constructor(private http: HttpClient, private auth: AuthService) { }

  setPermission(ethereumAccount: EthereumAccount, contract: string): Observable<any> {
    const headers = headersGenerator(true, true, this.auth.getToken());
    return this.http.put(`${this.url}${contract}`, ethereumAccount, { headers });
  }
}
