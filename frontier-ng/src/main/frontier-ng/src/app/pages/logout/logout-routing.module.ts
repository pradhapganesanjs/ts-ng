import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LogoutComponent } from './logout.component';

const routes: Routes = [
                { path: '', redirectTo: 'logout', pathMatch: 'full'  },
                { path: 'logout', component : LogoutComponent}
            ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LogoutRoutingModule { }
