import { Component, OnInit } from '@angular/core';
import { IAlertMsg, AlertType } from '@app/core';

@Component({
  selector: 'upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  alertErr: string;
  alertSuccess: string;

  constructor() { }


  ngOnInit() {
  }

  /*
  handleAlert(alert: IAlertMsg) {
    console.error('error caught ' + alert);
    if (!alert) {
      return;
    }

    if ('alert-danger' === alert.type) {
      this.alertErr = alert.msg;
    }else if ('alert-success' === alert.type) {
      this.alertSuccess = alert.msg;
    }
  }
  */

}
