import { Component, OnInit } from '@angular/core';
import { AlertService } from '../services/alert/alert.service';

@Component({
  selector: 'alert-msg',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
  // , providers: [ AlertService ]
})
export class AlertComponent implements OnInit {

  alertMsg: string;
  alertClass: string;

  constructor(private alertSrv: AlertService) {
    this.alertSrv.alertMsgSub.next(null);
  }

  ngOnInit() {

    this.alertSrv.alertMsgObs.subscribe(alertMap => {
      if (alertMap) {
        this.alertClass = alertMap.type.toString(); // get('alert-type');
        this.alertMsg = alertMap.msg;
      }
    });
  }

}
