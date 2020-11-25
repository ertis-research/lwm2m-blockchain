import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from "rxjs";

import { AclEntry } from "../models";
import { AuthService } from "./auth.service";
import { apiUrl } from "../core";
import { headersGenerator } from "../common";

@Injectable({
  providedIn: 'root'
})
export class AclService {

  url = `${apiUrl}acl/`

  constructor(private http: HttpClient, private auth: AuthService) { }

  getUserEntries(username: string): Observable<AclEntry[]> {
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<AclEntry[]>(this.url+username, { headers });
  }

  getUserEntriesByClient(username: string, client_name: string): Observable<AclEntry[]> {
    const headers = headersGenerator(false, true, this.auth.getToken());
    return this.http.get<AclEntry[]>(this.url+username+"/filterByClient/"+client_name, { headers });
  }

  updateEntry(username: string, entry: AclEntry): Observable<any> {
    const headers = headersGenerator(true, true, this.auth.getToken());
    return this.http.put(this.url+username, entry, { headers });
  }
}
