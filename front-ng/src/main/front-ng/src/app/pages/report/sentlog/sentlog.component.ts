import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AlertService, SelectItemMap } from '@app/core';
import { ReportSentLogService } from '../services/report-sentlog.service';

@Component({
  selector: 'report-sentlog',
  templateUrl: './sentlog.component.html',
  styleUrls: ['./sentlog.component.css']
})
export class SentlogComponent implements OnInit {

  private colHeadersSub = new BehaviorSubject<any[]>([]);
  colHeadersObs = this.colHeadersSub.asObservable();

  private rowDataSub = new BehaviorSubject<any[]>([]);
  rowDataObs = this.rowDataSub.asObservable();

  private dataSrcActionSub = new BehaviorSubject<string>(undefined);
  dataSrcActionObs = this.dataSrcActionSub.asObservable();

  private showLoadBusySub = new BehaviorSubject<boolean>(false);
  showLoadBusy$ = this.showLoadBusySub.asObservable();

  recordColumns: SelectItemMap[];

  blockDataRepo = [];

  inputval = '';
  selectedVal = 'select';

  limit;
  lastId;
  cacheBlockSizeIn = 500; // after each api call cache at page level

  placeHolder = 'Please Enter';
  constructor(private sentLogSrv: ReportSentLogService, private alertSrv: AlertService) {
    this.limit = this.cacheBlockSizeIn * 3;
  }

  ngOnInit() {
    this.initColHeaders();
    this.fetchAllByDefault();
    this.populateColmnDropdown();
  }

  fetchAllByDefault() {
    this.lastId = null;
    const repCrit = this.createReportCrit(null, null, null);
    this.blockDataRepo = [];

    const initCallBack = <ICallBack>{ callBack: () => this.publishInit() };

    this.reportByCritSrv(repCrit, initCallBack);
  }
  publishInit() {
    console.error(' publishInit ');
    this.dataSrcActionSub.next('init');
    this.rowDataSub.next(this.blockDataRepo);
  }


  fetchAction() {
    this.showLoadBusySub.next(true);
    this.lastId = null;
    const repCrit = this.createReportCrit(this.selectedVal, this.inputval, null);
    this.blockDataRepo = [];
    const newFetchtCallBack = <ICallBack>{ callBack: () => this.publishNewFetch() };

    this.reportByCritSrv(repCrit, newFetchtCallBack);
  }
  publishNewFetch() {
    console.error(' publishNewFetch ');
    this.dataSrcActionSub.next('reset');
    this.rowDataSub.next(this.blockDataRepo);
  }

  pullMoreData(lastIdP: string) {
    console.log(' pullMoreData ');
    const critKey = 'select' === this.selectedVal ? null : this.selectedVal;
    const critVal = this.inputval && '' !== this.inputval ? this.inputval : null;

    const repCrit = this.createReportCrit(critKey, critVal, this.lastId);
    const resetCallBack = <ICallBack>{ callBack: () => this.publishResp() };
    this.reportByCritSrv(repCrit);
  }
  publishResp() {
    console.error(' publishResp ');
    this.rowDataSub.next(this.blockDataRepo);
  }

  resetAction() {
    this.showLoadBusySub.next(true);
    this.inputval = '';
    // this.selectedVal = 'select';
    this.blockDataRepo.length = 0; // clear repo
    console.log(' clear repo ' + this.blockDataRepo.length);
    this.fetchAllByDefault();
    console.log(' inputval ' + this.inputval);
  }

  private reportByCritSrv(repCrit, iCallBack?: ICallBack) {
    console.log(`ready to submit key : ${this.selectedVal} val: ${this.inputval}`);

    this.sentLogSrv.reportSentLogLimitByCrit(repCrit)
      .subscribe(res => {
        if ('0' === res.error_num) {
          this.processSuccess(res.result);
          if (iCallBack) {
            iCallBack.callBack();
          }
        } else {
          this.handleErr(`${res.error_num} : ${res.error_msg}`);
        }
      }, error => {
        this.handleErr(error);
      });
  }
  private processSuccess(res) {
    const rowData = res.records;
    console.log(' Current: this.blockDataRepo size ' + this.blockDataRepo.length);
    if (rowData && rowData.length > 0) {
      this.blockDataRepo.push(...rowData); // all data repo
      console.log('New pulled records size ' + rowData.length);
      console.log(' Later this.blockDataRepo size ' + this.blockDataRepo.length);
      try {
        this.lastId = rowData[rowData.length - 1].id;
      } catch (e) { console.error(' this.lastId could not set for ' + rowData.length); }
    }
  }
  private initColHeaders(): void {
    this.sentLogSrv.getReportSentLogHeads()
      .subscribe(res => {
        if ('0' === res.error_num) {
          const result = res.result;
          const headers = result.headers;
          console.log('initColHeaders header ' + headers);
          this.colHeadersSub.next(headers);
        } else {
          this.handleErr(`${res.error_num} : ${res.error_msg}`);
        }
      });
  }

  populateColmnDropdown() {
    this.colHeadersObs.subscribe(heads => {
      if (heads) {
        const recColumnTmp = [];
        const repCrits: string[] = heads;

        repCrits.forEach(r => {
          const itm: SelectItemMap = new SelectItemMap();
          itm.value = r;
          itm.viewValue = r;
          recColumnTmp.push(itm);
        });
        this.recordColumns = recColumnTmp;
      }
    });
  }

  private handleErr(err) {
    this.alertSrv.showAlertErrMsg(err);
  }


  createReportCrit(critKey, critVal, lastId) {
    const repCrit = {};
    repCrit['critKey'] = critKey;
    repCrit['critVal'] = critVal;
    repCrit['lastId'] = lastId;
    repCrit['limit'] = this.limit;
    return repCrit;
  }
}

interface ICallBack {
  callBack?(): void;
}
