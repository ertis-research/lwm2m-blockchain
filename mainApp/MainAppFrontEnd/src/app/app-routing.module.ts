import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BootstrapServerComponent } from './components/bootstrap-server/bootstrap-server.component';
import { ServerComponent } from './components/server/server.component';


const routes: Routes = [
  {path: 'bs', component:BootstrapServerComponent},
  {path: 'server', component:ServerComponent},
  {path: '**', pathMatch:'full', redirectTo:'bs'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
