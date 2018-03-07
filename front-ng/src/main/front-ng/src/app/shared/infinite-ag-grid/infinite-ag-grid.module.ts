import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AgGridModule} from 'ag-grid-angular';
import { InfiniteAgGridComponent } from './infinite-ag-grid.component';

@NgModule({
  imports: [
    CommonModule,
    AgGridModule
  ],
  declarations: [InfiniteAgGridComponent],
  exports: [InfiniteAgGridComponent]
})
export class InfiniteAgGridModule { }
