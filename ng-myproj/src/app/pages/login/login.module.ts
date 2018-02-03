import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';

import { FormsModule } from '@angular/forms';

import { CoreModule } from '@app/core';
import { SharedModule } from '@app/shared';


import { LoginComponent } from './login.component';
import { LoginFormComponent } from './login-form/login-form.component';

import { LoginDashboardComponent } from './login-dashboard/login-dashboard.component';

@NgModule({
  declarations: [LoginComponent
    , LoginFormComponent, LoginDashboardComponent
    ]
  , imports: [
    CommonModule,
    HttpModule,
    FormsModule,

    CoreModule,
    SharedModule
  ]
  , exports: [
    LoginComponent,
    LoginDashboardComponent
  ]

})
export class LoginModule { }
