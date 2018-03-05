import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoutComponent } from './logout.component';
import { LogoutRoutingModule } from './logout-routing.module';
import { InlineConfirmModule } from '@app/shared';

@NgModule({
  imports: [
    CommonModule,
    LogoutRoutingModule,
    InlineConfirmModule
  ],
  declarations: [LogoutComponent]
})
export class LogoutModule { }
