import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpClientModule } from '@angular/common/http';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

// import { SharedModule } from '@app/shared';

import { HeaderComponent} from './header/header.component';
import { FooterComponent } from './footer/footer.component';

import { NavbarComponent } from './navbar/navbar.component';
import { UserService } from './services/user/user.service';
import { User } from './services/user/user.bo';
import { AlertComponent } from './alert/alert.component';
import { AlertService } from './services/alert/alert.service';
import { IAlertMsg } from './services/alert/i-alert-msg';
import { ErrorProcessor } from './services/helpers/error-processor';
import { SelectItemMap } from './utils/select-item-map';
import { StorageService } from './services/auth/storage.service';
import { HttpInterceptorService } from './services/auth/http-interceptor.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { SessionProcessor } from './services/helpers/session-processor';

@NgModule({
  declarations: [
    HeaderComponent
    , FooterComponent
    , NavbarComponent, AlertComponent
  ]
  , imports: [
              CommonModule,
              HttpClientModule,

              NgbModule.forRoot()

             // , SharedModule
            ]

  , exports: [
    HeaderComponent
    , FooterComponent
    , NavbarComponent,
    AlertComponent
  ]
  , providers: [
    UserService
    , User
    , AlertService
    , ErrorProcessor
    , SessionProcessor
    , SelectItemMap
    , StorageService
    , {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ]


})
export class CoreModule { }
