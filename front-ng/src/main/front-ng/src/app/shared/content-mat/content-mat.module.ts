import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material';

import { ContentMatComponent } from './content-mat.component';

@NgModule({
  imports: [
    CommonModule,
    MatCardModule
  ],
  declarations: [ContentMatComponent],
  exports: [ContentMatComponent]
})
export class ContentMatModule { }
