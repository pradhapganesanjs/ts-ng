import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputMatComponent } from './input-mat.component';
import { MatInputModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatInputModule
  ],
  declarations: [InputMatComponent],
  exports: [InputMatComponent]
})
export class InputMatModule { }
