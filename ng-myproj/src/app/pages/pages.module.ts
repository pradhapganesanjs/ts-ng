import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UserService } from '../services/user/user.service';
import { User } from '../services/user/user.bo';

import { LoginFormComponent } from './login-form/login-form.component';
import { ProfileComponent } from './profile/profile.component';
import { AccountsComponent } from './accounts/accounts.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
@NgModule({
  declarations: [LoginFormComponent, ProfileComponent, AccountsComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule
  ],
  exports : [
    LoginFormComponent,
    DashboardComponent,
    ProfileComponent,
    AccountsComponent
  ],
  providers : [
    UserService,
    User
  ]

})
export class PagesModule { }
