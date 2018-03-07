import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import { IDatasource, IGetRowsParams } from 'ag-grid/dist/lib/rowModels/iDatasource';

@Component({
  selector: 'infinite-ag-grid',
  templateUrl: './infinite-ag-grid.component.html',
  styleUrls: ['./infinite-ag-grid.component.css']
})
export class InfiniteAgGridComponent implements OnInit {

  @Input() colHeadersObsrv: Observable<any[]>;
  @Input() rowDataObsrv: Observable<any[]>;

  @Input() cacheBlockSizeIn: number;

  @Output() emitDataPuller = new EventEmitter<any>();

  private columnDefs = [];
  private headerStrArr = [];

  private rowDataSub = new BehaviorSubject<any[]>(undefined);
  private colmnSub = new BehaviorSubject<any[]>(undefined);

  rowDataObs = this.rowDataSub.asObservable();
  colmnObs = this.colmnSub.asObservable();

  private initDataSrcSub = new BehaviorSubject<boolean>(false);
  private initDataSrcObs = this.initDataSrcSub.asObservable();
  private initDSFlag = false; // init only once

  overlayNoRowsTemplate;
  overlayLoadingTemplate = `<div align="center"><p>Loading...</p><img src="assets/loading-magic-cube.gif"> </div>`;

  rowModelType = 'infinite';
  // cacheBlockSize = 5;

  private blockDataRepo = [];

  gDataSrc: IDatasource;

  getRowNodeId = function (item) {
    return item.id;
  };
  constructor() {
    console.log('InfiniteAgGridComponent loaded');
  }

  ngOnInit() {
    this.setNoRowTxt();
    this.colHeadersObsrv.subscribe(head => this.processHeaders(head));

    this.rowDataObsrvSubscribe();
    /*
    this.gDataSrc = this.getDataSource(this.blockDataRepo, this.emitDataPuller);
    */
  }

  rowDataObsrvSubscribe() {

    this.rowDataObsrv.subscribe(rows => {
      const rowData = this.processRowData(rows);
      if (rowData && rowData.length > 0) {
        // rowData.forEach(r => console.log(r.id));
        // console.log('new Data count ' + rowData.length);
        //  console.log('b4 newdata  push repo size : ' + this.blockDataRepo.length);

        this.blockDataRepo.push(...rowData);
        // console.log('after newdata  push repo size : ' + this.blockDataRepo.length);
        if (this.blockDataRepo.length > 0 && !this.initDSFlag) {
          this.initDataSrcSub.next(true);
        }
      }
    });
  }

  onGridReady(params) {
    params.api.showLoadingOverlay();
    this.initDataSrcObs.subscribe(initDS => {
      if (initDS) {
        this.initDSFlag = true;
        params.api.hideOverlay();
        this.gDataSrc = this.getDataSource(this.blockDataRepo, this.emitDataPuller, params);
      }
    });
  }

  private getDataSource(dataRepo: any[], emitPullData, gridParam): IDatasource {
    const dataSource = {
      rowCount: null,
      getRows: function (params: IGetRowsParams) {
        console.error('asking for ' + params.startRow + ' to ' + params.endRow);
        console.error('current repo size ' + dataRepo.length);
        emitPullData.emit('');
        gridParam.api.showLoadingOverlay();
        setTimeout(function () {
          gridParam.api.hideOverlay();
          let lastRow = -1;
          console.error('after emit repo size: ' + dataRepo.length);
          // dataRepo.forEach( e => console.log(e.id));
          if (dataRepo && dataRepo.length > 0) {
            const newBlockData = dataRepo.slice(params.startRow, params.endRow);
            if (dataRepo.length <= params.endRow) {
              lastRow = dataRepo.length;
            }
            params.successCallback(newBlockData, lastRow);
          }
        }, 300);
      }
    };
    return dataSource;
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
    // console.log('private processRowData ' + rows);
    if (!rows || rows.length === 0) { return null; }

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
