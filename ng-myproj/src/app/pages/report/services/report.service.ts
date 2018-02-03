import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';

import { deserialize } from 'json-typescript-mapper';

import { Report } from './report.bo';

import { ReportCriteria } from './report-criteria.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class ReportService {

  private glRecordList: Report[];

  private reportListSub = new BehaviorSubject<Report[]>(undefined);
  private reportListObserv: Observable<Report[]>;

  constructor(private httpSub: Http, private httpClient: HttpClient) {
    console.log('ReportService Construtor'
      + this.httpSub);
  }

  getReports() {
    return this.httpSub.get('/api/reports').map(data => {
      if (!data) { throw new Error('something went wrong'); }
      const recordList = [];
      for (const d of data.json()) {
        const desr: Report = deserialize(Report, d);
        recordList.push(desr);
      }
      this.reportListSub.next(recordList);
      // const reportRes = deserialize(Report, data['result']);
      return recordList;
    });
  }

  fetchReportsAll() {
    return this.httpSub.get('/api/reports').subscribe(data => {
      if (!data) { throw new Error('something went wrong'); }
      const recordList = [];
      for (const d of data.json()) {
        const desr: Report = deserialize(Report, d);
        recordList.push(desr);
      }
      this.reportListSub.next(recordList);
      // const reportRes = deserialize(Report, data['result']);
      return recordList;
    });
  }

  reports(): Observable<Report[]> {
    return this.reportListSub.asObservable();
  }

  reportByCriteria(repCrit: ReportCriteria) {
    console.log('repCrit ' + repCrit);
    if (repCrit) {
      console.log('repCrit status : ' + repCrit.status);
      console.log('repCrit id : ' + repCrit.id);
    }
    const headerParams = new HttpHeaders();
    headerParams.set('Content-Type', 'application/json');

    this.httpClient.post('/api/reportsbycrit', repCrit, { headers: headerParams }).subscribe(res => {
      console.log('res ' + res);
      this.reportListSub.next(<Report[]>res);
      this.glRecordList = <Report[]>res;
    });
  }

}
