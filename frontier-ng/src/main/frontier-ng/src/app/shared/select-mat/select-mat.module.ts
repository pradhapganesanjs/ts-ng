import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectMatComponent } from './select-mat.component';
import {  MatSelectModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule
    , MatSelectModule
  ]
  , declarations: [SelectMatComponent]
  , exports: [SelectMatComponent]
})
export class SelectMatModule { }
