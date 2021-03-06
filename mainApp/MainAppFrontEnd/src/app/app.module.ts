import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service'; //Cookie service library

//routes
import { AppRoutingModule } from './app-routing.module';

//services
import { ClientService } from './services/client.service';
import { AnomalyService } from './services/anomaly.service';
import { UserService } from './services/user.service';
import { AuthService } from "./services/auth.service";
import { LoginService } from "./services/login.service";
import { LoggedGuard } from "./services/guards.service";
import { ErrorService } from "./services/error.service";

//components
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { ClientComponent } from './components/client/client.component';
import { AnomalyComponent } from './components/anomaly/anomaly.component';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from './components/login/login.component';
import { UserEntryComponent } from './components/user-entry/user-entry.component';

//pipes
import { RoleNumberToStringPipe } from './pipes/role-number-to-string.pipe';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ClientComponent,
    AnomalyComponent,
    UserComponent,
    LoginComponent,
    RoleNumberToStringPipe,
    UserEntryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    ClientService,
    AnomalyService,
    UserService,
    AuthService,
    LoginService,
    LoggedGuard,
    CookieService, //Cookie service library
    ErrorService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
