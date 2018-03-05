import { Component, OnInit } from '@angular/core';
import { ReportService } from '../services/report.service';
import { AlertService } from '@app/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { ReportTransRes } from '../services/report-trans-res-bo';

@Component({
  selector: 'app-report-limit',
  templateUrl: './report-limit.component.html',
  styleUrls: ['./report-limit.component.css']
})
export class ReportLimitComponent implements OnInit {

  private colHeadersSub = new BehaviorSubject<any[]>(undefined);
  private rowDataSub = new BehaviorSubject<any[]>(undefined);

  colHeadersObs = this.colHeadersSub.asObservable();
  rowDataObs = this.rowDataSub.asObservable();

  sindex;
  limit;

  lastId;
  cacheBlockSizeIn = 500; // after each api call cache at page level

  constructor(private reportService: ReportService, private alertSrv: AlertService) {
    this.sindex = 0;
    this.limit = 1000; // each time pull 1000 records
    // this.cacheBlockSizeIn = 5;
  }

  ngOnInit() {
    this.reportsByLimitSrv();
  }

  fetchMore() {
    this.sindex = this.sindex + this.limit;
    this.reportsByLimitSrv();
  }

  pullMoreData(lastIdP: string) {
    console.log(' pullMoreData ' );
    this.reportsByLimitSrv();
  }

  private reportsByLimitSrv() {
    this.reportService.reportByLimit(this.lastId, this.limit)
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

  private processSuccess(res) {
    console.log(res);
    if (null === res.records
          || undefined === res.records
          || res.records.length === 0) {
      this.sindex = 0;
    }
    this.colHeadersSub.next(res.headers);
    if (res.records && res.records.length > 0) {
      console.log(' size ' + res.records.length);
      try {
        this.lastId = res.records[res.records.length - 1].id;
      } catch (e) { console.error(' this.lastId could not set for ' + res.records.length) }
      // this.cacheBlockSizeIn = this.cacheBlockSizeIn + res.records.length;
    }
    this.rowDataSub.next(res.records);
  }

  private handleErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }

}
