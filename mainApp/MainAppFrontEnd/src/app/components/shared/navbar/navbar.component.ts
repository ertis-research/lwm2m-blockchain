import { Component } from '@angular/core';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styles: []
})
export class NavbarComponent {

  username: string = "";

  constructor(private auth: AuthService) { }

  isAuthenticated(): boolean {
    if(this.auth.isAuthenticated()){
      this.username = this.auth.getUsername();
      return true;
    }
    return false;
  }

  isAdmin(): boolean {
    if(this.auth.getRole() === 1) {
      return true;
    }
    return false;
  }

  logout() {
    this.auth.logout();
  }
}
