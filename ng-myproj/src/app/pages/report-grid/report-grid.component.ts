import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportService } from '../../services/report/report.service';
import { AgGridModule } from 'ag-grid-angular/main';
import { GridOptions } from 'ag-grid/main';
import { RedComponent } from './red/red.component';
import { Report } from '../../services/report/report.bo';
import { DataSource } from '@angular/cdk/collections';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';


import { AfterViewInit } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-report-grid',
  templateUrl: './report-grid.component.html',
  styleUrls: ['./report-grid.component.css']
})
export class ReportGridComponent implements OnInit, AfterViewInit {

  private recordsList: Report[];
  private reportDataSrc: MatTableDataSource<Report>;

  displayColumns = [];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private reportService: ReportService) {
    this.reportDataSrc = new MatTableDataSource<Report>();
  }

  ngOnInit() {
    this.reportService.getReports().subscribe(data => this.handleResp(data), err => this.handleErr(err));

    const prop = Object.getOwnPropertyNames(new Report());
    const propAsColumns = [];
    for (const p of prop) {
      propAsColumns.push(p);
    }
    console.log(propAsColumns);
    this.displayColumns = propAsColumns;
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
    const rows = new Array<Report>();
    this.recordsList = reports;
    // this.reportDataSrc = new MatTableDataSource<Report>(reports);
    this.reportDataSrc.data = reports;
  }
  handleErr(err) {
    console.log('err ' + err);
  }

}

