import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule, MatButtonModule } from '@angular/material';
import { InlineConfirmComponent } from './inline-confirm.component';

@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule
  ],
  declarations: [InlineConfirmComponent],
  exports: [InlineConfirmComponent]
})
export class InlineConfirmModule { }
