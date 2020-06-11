import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClientComponent } from './components/client/client.component';
import { LoginComponent } from './components/login/login.component';
import { UnLoggedGuard, LoggedGuard } from './services/guards.service';


const routes: Routes = [
  { path: 'login/:expired', component: LoginComponent , canActivate: [UnLoggedGuard] },
  { path: 'login', component: LoginComponent , canActivate: [UnLoggedGuard] },
  { path: 'clients', component: ClientComponent, canActivate: [LoggedGuard] },
  { path: '**', pathMatch: 'full', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
