import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

//routes
import { AppRoutingModule } from './app-routing.module';

//services
import { ClientService } from './services/client.service';
import { AnomalyService } from './services/anomaly.service';
import { UserService } from './services/user.service';

//components
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { ClientComponent } from './components/client/client.component';
import { AnomalyComponent } from './components/anomaly/anomaly.component';
import { UserComponent } from './components/user/user.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ClientComponent,
    AnomalyComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ClientService,
    AnomalyService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
