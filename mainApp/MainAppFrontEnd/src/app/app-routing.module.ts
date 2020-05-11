import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientComponent } from './components/client/client.component';
import { AnomalyComponent } from './components/anomaly/anomaly.component';
import { UserComponent } from './components/user/user.component';


const routes: Routes = [
  {path: 'clients', component:ClientComponent},
  {path: 'anomalies', component:AnomalyComponent},
  {path: 'users', component:UserComponent},
  {path: '**', pathMatch:'full', redirectTo:'clients'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
