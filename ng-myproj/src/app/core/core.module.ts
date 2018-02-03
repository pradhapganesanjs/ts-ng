import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SharedModule } from '@app/shared';

import { HeaderComponent} from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ErrorComponent } from './alert/error/error.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserService } from './services/user/user.service';
import { User } from './services/user/user.bo';



@NgModule({
  declarations: [
    HeaderComponent
    , FooterComponent
    , ErrorComponent
    , NavbarComponent
  ]
  , imports: [
              CommonModule,
              HttpClientModule,

              NgbModule.forRoot()

              , SharedModule ]

  , exports: [
    HeaderComponent
    , FooterComponent
    , ErrorComponent
    , NavbarComponent
  ]
  , providers: [
    UserService,
    User
  ]


})
export class CoreModule { }
