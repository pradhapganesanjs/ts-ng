import { Component, OnInit } from '@angular/core';
import { AlertService, IAlertMsg, AlertType } from '@app/core';

import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';

import { ReportService } from '../services/report.service';
import { ReportTransRes } from '../services/report-trans-res-bo';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Component({
  selector: 'app-report-all',
  templateUrl: './report-all.component.html',
  styleUrls: ['./report-all.component.css']
})
export class ReportAllComponent implements OnInit {

  private colHeadersSub = new BehaviorSubject<any[]>(undefined);
  private rowDataSub = new BehaviorSubject<any[]>(undefined);

  colHeadersObs = this.colHeadersSub.asObservable();
  rowDataObs = this.rowDataSub.asObservable();

  constructor(private reportService: ReportService, private alertSrv: AlertService) { }

  ngOnInit() {
    this.reportService.getReportingTrans()
      .subscribe(res => {
        this.handleRes(res);
      }, error => {
        this.handleErr(error);
      });
  }

  private handleRes(res: ReportTransRes) {
    console.log(' component ' + res + ' headers ' + res.result.headers);
    if ('0' === res.error_num) {
      this.processSuccess(res.result);
    } else {
      this.handleErr(`${res.error_num} : ${res.error_msg}`);
    }

  }

  private handleErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }

  private processSuccess(res) {
    console.log(res);

    this.colHeadersSub.next(res.headers);
    this.rowDataSub.next(res.records);
  }

}
