import { Component, OnInit } from '@angular/core';

import { AlertService } from '@app/core';

import { SCRIPTS } from '../upload-scripts';
import { UploadService } from '../services/upload.service';
import { UploadResponse } from '../services/upload-response-bo';

@Component({
  selector: 'upload-config',
  templateUrl: './upload-config.component.html',
  styleUrls: ['./upload-config.component.css']
})
export class UploadConfigComponent implements OnInit {

  script = SCRIPTS;

  fileToUpload: File = null;
  fileUploadType = 'select';

  constructor(private uploadService: UploadService, private alertSrv: AlertService) { }

  ngOnInit() {
  }

  uploadFile() {
    console.log('asdf');
    console.log(' fileToUpload ' + this.fileToUpload);
    console.log(' fileUploadType ' + this.fileUploadType);
    if (this.fileToUpload && this.fileUploadType) {
      this.uploadSubmit();
    }
  }

  addFileToUpload(files: FileList) {
    if (files.length > 0) {
      this.fileToUpload = files.item(0);
      console.log('file ' + this.fileToUpload);
    }
  }

  uploadSubmit() {
    this.uploadService.uploadConfig(this.fileToUpload, this.fileUploadType)
      .subscribe(res => {
        this.handleRes(res);
      }, error => {
        this.handleErr(error);
      });
  }

  private handleRes(res: UploadResponse) {
    if (res && '0' === res.error_num || '' === res.error_num) {
      this.showSuccess(res.result ? res.result : 'Success');
    } else if (res) {
      this.showErr(`${res.error_num} : ${res.error_msg}`);
    } else {
      this.showErr(`Failed`);
    }
  }
  private handleErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }

  private showSuccess(sMsg) {
    this.alertSrv.showAlertSuccessMsg(sMsg);
  }
  private showErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }

}
