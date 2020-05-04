import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

//routes
import { AppRoutingModule } from './app-routing.module';

//services
import { ApiService } from './services/api.service';

//components
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { BootstrapServerComponent } from './components/bootstrap-server/bootstrap-server.component';
import { ServerComponent } from './components/server/server.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    BootstrapServerComponent,
    ServerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
