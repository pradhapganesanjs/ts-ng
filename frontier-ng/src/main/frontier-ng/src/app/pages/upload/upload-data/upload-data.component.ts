import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UploadService } from '../services/upload.service';
import { SCRIPTS } from '../upload-scripts';
import { SelectItemMap } from '@app/core';
import { UploadResponse } from '../services/upload-response-bo';
import { AlertService} from '@app/core';

@Component({
  selector: 'upload-data',
  templateUrl: './upload-data.component.html',
  styleUrls: ['./upload-data.component.css']
})
export class UploadDataComponent implements OnInit {
  message = '';
  scripts = SCRIPTS;
  filetypes: SelectItemMap[];
  selFileType = 'select';
  private fileToUpload: File;
  isReadyToSubmit = false;

  constructor(private uploadService: UploadService, private alertSrv: AlertService) { }

  ngOnInit() {
    this.alertSrv.alertMsgSub.next(null);
    this.populateDropDown();
  }

  uploadFile(files: FileList) {
    if (files.length > 0) {
      this.fileToUpload = files.item(0);
      console.log('file ' + this.fileToUpload);
    }
  }

  uploadSubmit() {

    if (!this.isSubmittable()) {
      return;
    }
    this.uploadService.upload(this.fileToUpload, this.selFileType)
      .subscribe(res => {
        console.log('Success ' + res);
        this.handleRes(res);
        this.resetForm();
      }, error => {
        this.handleErr(error);
        this.resetForm();
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
    this.showErr(err);
  }

  private showSuccess(sMsg) {
    this.alertSrv.showAlertSuccessMsg(sMsg);
  }
  private showErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }

  isSubmittable() {
    const submittable = (this.selFileType && this.fileToUpload) ? true : false;
    if (!submittable) { this.alertSrv.showAlertErrMsg('Please attach a file'); }
    return submittable;
  }


  private resetForm() {
    this.fileToUpload = undefined;
    this.selFileType = '';
    this.populateDropDown();
  }

  private populateDropDown() {
    const tmpFiletypes: SelectItemMap[] = [];
    const itm1: SelectItemMap = new SelectItemMap();
    itm1.value = SCRIPTS._sel_clientconfig_key;
    itm1.viewValue = SCRIPTS._sel_clientconfig_val;

    const itm2: SelectItemMap = new SelectItemMap();
    itm2.value = SCRIPTS._sel_reportconfig_key;
    itm2.viewValue = SCRIPTS._sel_reportconfig_val;

    tmpFiletypes.push(itm1);
    tmpFiletypes.push(itm2);
    this.filetypes = tmpFiletypes;
  }
}
