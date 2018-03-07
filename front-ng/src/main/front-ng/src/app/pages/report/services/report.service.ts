import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { deserialize } from 'json-typescript-mapper';

import { Report } from './report.bo';

import { ReportCriteria } from './report-criteria.model';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { ReportTransRes } from './report-trans-res-bo';
import { ApiUrl, ErrorProcessor } from '@app/core';


import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

@Injectable()
export class ReportService {
  /*
  private glRecordList: Report[];

  private reportListSub = new BehaviorSubject<Report[]>(undefined);
  private reportListObserv: Observable<Report[]>;

  private reportTransSub = new BehaviorSubject<ReportTransRes>(undefined);
  private reportTransObs = new Observable<ReportTransRes>(undefined);
*/
  httpHeaders;

  constructor(private httpClient: HttpClient, private errPros: ErrorProcessor) {
    console.log('ReportService Construtor');
    let headersTmp = new HttpHeaders();
    headersTmp = headersTmp.append('Content-Type', 'application/json');
    this.httpHeaders = { headers: headersTmp };
  }

  getReportHeads() {
    return this.httpClient.get(ApiUrl.REPORT_HEADERS)
    .map(resp => {
          return this.handleResp(resp); }
         ,
          err => {
            return Observable.of(this.handleErr(err)); })
    .catch(err => {
              return Observable.throw(this.errPros.getErrorMsg(err)); });
  }

  getReportingTrans(): Observable<ReportTransRes> {
    return this.httpClient.get(ApiUrl.REPORT_TRANS)
                          .map(resp => {
                                return this.handleResp(resp); }
                               ,
                                err => {
                                  return Observable.of(this.handleErr(err)); })
                          .catch(err => {
                                    return Observable.throw(this.errPros.getErrorMsg(err)); });
  }


  reportByCriteria(repCrit) {
    if (repCrit) {
      console.log('repCrit stringfy : ' + JSON.stringify(repCrit));
    }
    return this.httpClient.post(ApiUrl.REPORT_BY_CRIT, repCrit, this.httpHeaders) //
                          .map(resp => {
                                return this.handleResp(resp); }
                               , err => {
                                  return Observable.of(this.handleErr(err)); })
                          .catch(err => {
                                    return Observable.throw(this.errPros.getErrorMsg(err)); });
  }

  reportLimitByCriteria(repCritParam) {
    if (repCritParam) {
      console.log('repCrit stringfy : ' + JSON.stringify(repCritParam));
    }
    const repCrit = {};
    repCrit['critKey'] = repCritParam.critKey;
    repCrit['critVal'] = repCritParam.critVal;
    repCrit['limit'] = repCritParam.limit;
    repCrit['lastId'] = repCritParam.lastId;

    return this.httpClient.post(ApiUrl.REPORT_LIMIT_BY_CRIT, repCrit, this.httpHeaders) //
                          .map(resp => {
                                return this.handleResp(resp); }
                               , err => {
                                  return Observable.of(this.handleErr(err)); })
                          .catch(err => {
                                    return Observable.throw(this.errPros.getErrorMsg(err)); });
  }
  reportByLimit(lastId, limit) {

    const repLimit = {};
    repLimit['lastId'] = lastId;
    repLimit['limit'] = limit;

    if (repLimit) {
      console.log('repCrit stringfy : ' + JSON.stringify(repLimit));
    }
    return this.httpClient.post(ApiUrl.REPORT_BY_LIMIT, repLimit, this.httpHeaders) //
                          .map(resp => {
                                return this.handleResp(resp); }
                               , err => {
                                  return Observable.of(this.handleErr(err)); })
                          .catch(err => {
                                    return Observable.throw(this.errPros.getErrorMsg(err)); });
  }

  private handleResp(resp: any): ReportTransRes {
    console.log('post resp ' + resp);
    const desReport = {} as ReportTransRes;
    desReport.error_num = resp.error_num;
    desReport.error_msg = resp.error_msg;
    desReport.result = resp.result;
    return desReport;
  }

  private handleErr(err): ReportTransRes {
    console.error(' err ' + err);
    const upRes = new ReportTransRes();
    upRes.error_num = '-100';
    upRes.error_msg = this.errPros.getErrorMsg(err);
    upRes.result = null;
    return upRes;
  }

}
