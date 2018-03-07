import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimpleAgGridComponent } from './simple-ag-grid.component';
import {AgGridModule} from 'ag-grid-angular';


@NgModule({
  imports: [
    CommonModule
    , AgGridModule.withComponents([ ])
  ],
  declarations: [SimpleAgGridComponent]
  , exports: [
    SimpleAgGridComponent
  ]
})
export class SimpleAgGridModule { }
