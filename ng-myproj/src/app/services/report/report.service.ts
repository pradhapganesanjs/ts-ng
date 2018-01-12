import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';

import { deserialize } from 'json-typescript-mapper';

import { Report } from './report.bo';
import { ReportComponent } from '../../core/report/report.component';

@Injectable()
export class ReportService {

  constructor(private httpSub: Http) {
    console.log('ReportService Construtor'
      + this.httpSub);
  }

  getReports() {

    return this.httpSub.get('/api/reports').map(data => {
      console.log(`http res: ${data}`);
      if (!data) { throw new Error('something went wrong'); }
      const recordList = [];
      for (const d of data.json()) {
        const desr: Report = deserialize(Report, d);
        console.log('desr.ID: ' + desr.id);
        recordList.push(desr);
      }
      // const reportRes = deserialize(Report, data['result']);
      return recordList;
    });
  }

}
