import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Components
import { ClientComponent } from './components/client/client.component';
import { AnomalyComponent } from './components/anomaly/anomaly.component';
import { UserComponent } from './components/user/user.component';
import { LoginComponent } from "./components/login/login.component";
import { AclComponent } from "./components/acl/acl.component";
import { WhitelistComponent } from "./components/whitelist/whitelist.component";

// Guards
import { LoggedGuard, AdminGuard, UnLoggedGuard } from './services/guards.service';

const routes: Routes = [
  { path: 'login/:expired', component: LoginComponent, canActivate: [UnLoggedGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnLoggedGuard] },
  { path: 'clients', component: ClientComponent, canActivate: [AdminGuard] },
  { path: 'anomalies', component: AnomalyComponent, canActivate: [LoggedGuard] },
  { path: 'users', component: UserComponent, canActivate: [AdminGuard] },
  { path: 'acl', component: AclComponent, canActivate: [AdminGuard] }, 
  { path: 'whitelist', component: WhitelistComponent, canActivate: [AdminGuard] },
  { path: '**', pathMatch: 'full', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
