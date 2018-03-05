import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InfiniteRefAgGridComponent } from './infinite-ref-ag-grid.component';
import { AgGridModule } from 'ag-grid-angular';

@NgModule({
  imports: [
    CommonModule,
    AgGridModule
  ],
  declarations: [InfiniteRefAgGridComponent],
  exports: [ InfiniteRefAgGridComponent ]
})
export class InfiniteRefAgGridModule { }
