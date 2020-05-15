import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Components
import { ClientComponent } from './components/client/client.component';
import { AnomalyComponent } from './components/anomaly/anomaly.component';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from "./components/login/login.component";

// Guards
import { LoggedGuard } from './services/guards.service';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'clients', component: ClientComponent, canActivate: [LoggedGuard] },
  { path: 'anomalies', component: AnomalyComponent, canActivate: [LoggedGuard] },
  { path: 'users', component: UserComponent, canActivate: [LoggedGuard] },
  { path: '**', pathMatch: 'full', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
