import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { MatTableModule, MatSortModule,   MatPaginatorModule } from '@angular/material';
import {CdkTableModule} from '@angular/cdk/table';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { UserService } from '../services/user/user.service';
import { User } from '../services/user/user.bo';
import { RouteConfig } from '../app.routing';

import { LoginFormComponent } from './login-form/login-form.component';
import { BaseService } from '../services/base/base.service';
import { BaseServiceReqBo } from '../services/base/base.service.req';
import { BaseServiceResBo } from '../services/base/base.service.res';
import { UploadComponent } from './upload/upload.component';
import { ReportGridComponent } from './report-grid/report-grid.component';
import { ReportService } from '../services/report/report.service';
import { AgGridModule } from 'ag-grid-angular';
import { RedComponent } from './report-grid/red/red.component';


/*
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';
*/


@NgModule({
declarations: [LoginFormComponent, UploadComponent, ReportGridComponent, RedComponent /*, ProfileComponent, AccountsComponent*/],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    RouteConfig,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    BrowserAnimationsModule
  ],
  exports : [
    LoginFormComponent,
    ReportGridComponent,
    CdkTableModule,
    MatSortModule,
    MatPaginatorModule
  ],
  providers : [
    BaseService,
    BaseServiceReqBo,
    BaseServiceResBo,
    UserService,
    User,
    ReportService
  ]

})
export class PagesModule { }
