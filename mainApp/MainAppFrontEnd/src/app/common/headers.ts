import { HttpHeaders } from '@angular/common/http';

export const headersGenerator = (app_json: boolean, jwt: boolean, token?: string): HttpHeaders => {
  let headers = new HttpHeaders();
  if(app_json) {
    headers = headers.set('Content-Type', 'application/json');
  }
  if(jwt) {
    headers = headers.set('Authorization', token);
  }
  return headers;
}