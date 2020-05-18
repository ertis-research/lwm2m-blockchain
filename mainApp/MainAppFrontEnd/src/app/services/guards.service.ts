import { Injectable } from '@angular/core';
import {
  Router, 
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoggedGuard implements CanActivate {

  constructor(
    private router:Router,
    private auth:AuthService
  ) { }
            
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if(this.auth.isAuthenticated()){
      return true;
    }else{
      console.error("You have not permission to view this page");
      this.router.navigate(['/login']);
      return false;
    }
  }
}

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private router:Router,
    private auth:AuthService
  ) { }
            
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if(this.auth.getRole() === 1){
      return true;
    }else{
      console.error("You have not permission to view this page");
      this.router.navigate(['/login']);
      return false;
    }
  }
}
