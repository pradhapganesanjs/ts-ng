import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UploadRoutingModule } from './upload-routing.module';
import { UploadConfigComponent } from './upload-config/upload-config.component';
import { UploadDataComponent } from './upload-data/upload-data.component';
import { UploadService } from './services/upload.service';
import { UploadResponse } from './services/upload-response-bo';
import { HttpClientModule } from '@angular/common/http';

import { CoreModule } from '@app/core';
import { UploadComponent } from './upload.component';

import { SelectMatModule } from '@app/shared';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    UploadRoutingModule,
    HttpClientModule
    , CoreModule
    , SelectMatModule
  ],
  declarations: [UploadConfigComponent, UploadDataComponent, UploadComponent],
  providers : [ UploadService, UploadResponse ]
})
export class UploadModule { }
