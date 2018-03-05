import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRouteModule } from './app.routing';

import { CoreModule } from './core/core.module';
import { LoginModule } from '@app/pages/login';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { DashboardComponent } from '@app/dashboard';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
                AppComponent,
                HomeComponent,
                DashboardComponent]
 , imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NgbModule.forRoot(),
    AppRouteModule,
    CoreModule,
    LoginModule
 ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
