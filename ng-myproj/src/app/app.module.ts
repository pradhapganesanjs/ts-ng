import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { RouteConfig } from './app.routing';

import { PagesModule } from './pages/pages.module';

import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { LoginFormComponent } from './pages/login-form/login-form.component';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    PagesModule,
    RouteConfig
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
