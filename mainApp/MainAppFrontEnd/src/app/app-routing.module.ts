import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BootstrapServerComponent } from './components/bootstrap-server/bootstrap-server.component';


const routes: Routes = [
  {path: 'bs', component:BootstrapServerComponent},
  {path: '**', pathMatch:'full', redirectTo:'bs'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
