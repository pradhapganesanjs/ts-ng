import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { UserService } from '../services/user/user.service';
import { User } from '../services/user/user.bo';

import { LoginFormComponent } from './login-form/login-form.component';
@NgModule({
  declarations: [LoginFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule
  ],
  exports : [
    LoginFormComponent
  ],
  providers : [
    UserService,
    User
  ]

})
export class PagesModule { }
