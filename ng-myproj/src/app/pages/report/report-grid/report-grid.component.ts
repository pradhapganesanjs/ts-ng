import { Component, OnInit, ViewChild, DoCheck } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { AfterViewInit } from '@angular/core';

// import { ReportService, Report } from '@app/pages/report'; //../../services/report/report.service';
import { ReportService } from '../services/report.service';
import { Report } from '../services/report.bo';

import { AgGridModule } from 'ag-grid-angular/main';
import { GridOptions } from 'ag-grid/main';
import { RedComponent } from './red/red.component';
// import { Report } from '../../services/report/report.bo';


@Component({
  selector: 'report-grid',
  templateUrl: './report-grid.component.html',
  styleUrls: ['./report-grid.component.css']
})
export class ReportGridComponent implements OnInit, AfterViewInit {

  // private recordsList: Report[];
  private reportDataSrc: MatTableDataSource<Report>;

  displayColumns = [];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private reportService: ReportService) {
  }
  // ngDoCheck() {
  //   console.log("ngDoCheck");
  //   this.reportService.reports()
  //   .subscribe( reps => this.handleResp(reps)
  //              , err => this.handleErr(err));
  // }
  ngOnInit() {
    this.reportDataSrc = new MatTableDataSource<Report>();
     this.reportService.getReports().subscribe(data => this.handleResp(data), err => this.handleErr(err));
    /*
     this.reportService.fetchReportsAll();
    this.reportService.reports()
                      .subscribe( reps => this.handleResp(reps)
                                 , err => this.handleErr(err));
                                 */

    const prop = Object.getOwnPropertyNames(new Report());
    const propAsColumns = [];
    for (const p of prop) {
      propAsColumns.push(p);
    }
    console.log(propAsColumns);
    this.displayColumns = propAsColumns;

    console.log( ' ' + this.reportService.reportByCriteria({id: 'ID_10'}));
  }

  /**
   * Set the sort after the view init since this component will
   * be able to query its view for the initialized sort.
   */
  ngAfterViewInit() {
    this.reportDataSrc.sort = this.sort;
    this.reportDataSrc.paginator = this.paginator;
  }

  handleResp(reports) {
    console.log('handleresp : ' + reports);
    const rows = new Array<Report>();
    // this.recordsList = reports;
    // this.reportDataSrc = new MatTableDataSource<Report>(reports);
    this.reportDataSrc.data = reports;
  }
  handleErr(err) {
    console.log('err ' + err);
  }

}
/*
export class ReportDataSource extends DataSource<Report> {

  constructor(private reportService: ReportService) {

  }

  connect() {
    new ReportGridComponent(this.reportService).
  }

}
*/
