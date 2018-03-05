import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { CoreModule, StorageService, HttpInterceptorService } from '@app/core';
import { SimpleAgGridModule, SelectMatModule, InputMatModule } from '@app/shared';

import { ReportRoutingModule } from './report-routing.module';
import { ReportService } from './services/report.service';
import { ReportComponent } from './report.component';
import { ReportAllComponent } from './report-all/report-all.component';
import { ReportFetchComponent } from './report-fetch/report-fetch.component';
import { ReportLimitComponent } from './report-limit/report-limit.component';
import { InfiniteAgGridModule } from '@app/shared';
import { InfiniteRefAgGridModule } from '@app/shared';

import { AgGridModule } from 'ag-grid-angular';
import { SentlogComponent } from './sentlog/sentlog.component';
import { ReportSentLogService } from './services/report-sentlog.service';


@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    HttpClientModule,
    ReportRoutingModule,
    AgGridModule,
    CoreModule,
    SimpleAgGridModule,
    InfiniteAgGridModule,
    InfiniteRefAgGridModule,
    SelectMatModule,
    InputMatModule
  ]
  , declarations: [ReportComponent
                    , ReportAllComponent
                    , ReportFetchComponent, ReportLimitComponent, SentlogComponent]
  , exports: [ ]
  , providers: [ ReportService, ReportSentLogService,
    StorageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ]
})
export class ReportModule {}
