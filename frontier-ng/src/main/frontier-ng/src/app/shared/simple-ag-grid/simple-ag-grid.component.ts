import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';

@Component({
  selector: 'simple-ag-grid',
  templateUrl: './simple-ag-grid.component.html',
  styleUrls: ['./simple-ag-grid.component.css']
})
export class SimpleAgGridComponent implements OnInit {

  @Input() colHeadersObsrv: Observable<any[]>;
  @Input() rowDataObsrv: Observable<any[]>;

  // private rowData = [];
  private columnDefs = [];
  private headerStrArr = [];

  private rowDataSub = new BehaviorSubject<any[]>(undefined);
  private colmnSub = new BehaviorSubject<any[]>(undefined);

  rowDataObs = this.rowDataSub.asObservable();
  colmnObs = this.colmnSub.asObservable();


  overlayNoRowsTemplate;

  constructor() { }

  ngOnInit() {
    this.setNoRowTxt();
    this.colHeadersObsrv.subscribe(head => this.processHeaders(head));
  }

  onGridReady(params) {
    this.rowDataObsrv.subscribe(rows => {
      const rowData = this.processRowData(rows);
      console.log('processRowData ' + rowData);

      params.api.setRowData(rowData);

      if (rowData && rowData.length > 0) {
        console.log('hideOverlay ');
        params.api.hideOverlay();
      } else {
        console.log('showNoRowsOverlay ');
        params.api.showNoRowsOverlay();
      }
    });
  }

  private processHeaders(heads) {
    // console.log(' processHeaders ' + heads);
    if (!heads) { return; }

    this.headerStrArr = heads;
    heads.forEach(h => {
      this.columnDefs.push({
        headerName: h
        , field: h
        , width: 100
      });
    });
    this.colmnSub.next(this.columnDefs);
  }

  private processRowData(rows: any[]): any[] {
    console.log('private processRowData ' + rows);
    if (!rows || rows.length === 0) { return []; }

    const rowData = [];
    for (const d of rows) {
      const newData = {};
      this.headerStrArr.forEach(h => Object.defineProperty(newData, h, { value: d[h] }));
      rowData.push(newData);
    }
    return rowData;
  }

  private setNoRowTxt() {
    this.overlayNoRowsTemplate = `No Records were fetched. Please try again later.`;
  }
}
