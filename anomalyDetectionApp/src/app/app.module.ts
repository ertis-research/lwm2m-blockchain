import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';

//routes
import { AppRoutingModule } from './app-routing.module';

//services
import { ClientService } from './services/client.service';
import { AnomalyService } from './services/anomaly.service';
import { LoginService } from './services/login.service';
import { AuthService } from './services/auth.service';
import { LoggedGuard } from './services/guards.service';
import { ErrorService } from './services/error.service';

//components
import { AppComponent } from './app.component';
import { ClientComponent } from './components/client/client.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientComponent,
    LoginComponent,
    NavbarComponent,
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
    LoginService,
    AuthService,
    LoggedGuard,
    CookieService,
    ErrorService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
