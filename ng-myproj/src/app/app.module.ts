import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HomeModule } from './home/home.module'

import { RouteConfig } from './app.routing'

import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { LoginFormComponent } from './pages/login-form/login-form.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginFormComponent
  ],
  imports: [
    BrowserModule,
    HomeModule,
    RouteConfig
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 


}
