import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
//import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatPaginatorModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule,
} from '@angular/material';

import { ReportGridComponent } from './report-grid/report-grid.component';
import { ReportContainerComponent } from './report-container/report-container.component';
// import { ReportContainerComponent, ReportGridComponent, ReportPanelComponent } from './index';
import { ReportPanelComponent } from './report-panel/report-panel.component';
import { ReportRoutingModule } from './report-routing.module';
// import { ReportPanelComponent } from './index';

import { SharedModule } from '@app/shared';
import { ReportService } from './services/report.service';


@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    HttpClientModule,
    ReportRoutingModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    SharedModule
  ]
  , declarations: [ReportContainerComponent, ReportGridComponent, ReportPanelComponent]
  , exports: [
      ReportContainerComponent, ReportGridComponent, ReportPanelComponent
  ],
  providers: [
    ReportService
  ]
})
export class ReportModule { }
