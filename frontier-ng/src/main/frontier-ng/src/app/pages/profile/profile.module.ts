import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { ContentMatModule } from '@app/shared';

@NgModule({
  imports: [
    CommonModule,
    ProfileRoutingModule,
    ContentMatModule
  ],
  declarations: [ProfileComponent]
})
export class ProfileModule { }
