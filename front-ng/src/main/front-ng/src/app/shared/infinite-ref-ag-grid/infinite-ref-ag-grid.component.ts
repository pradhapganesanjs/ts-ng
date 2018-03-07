import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import { IDatasource, IGetRowsParams } from 'ag-grid/dist/lib/rowModels/iDatasource';
import { GridOptions } from 'ag-grid/dist/lib/entities/gridOptions';

@Component({
  selector: 'infinite-ref-ag-grid',
  templateUrl: './infinite-ref-ag-grid.component.html',
  styleUrls: ['./infinite-ref-ag-grid.component.css']
})
export class InfiniteRefAgGridComponent implements OnInit {

  @Input() colHeaders$: Observable<any[]>;
  @Input() rowDataRepo$: Observable<any[]>;
  @Input() dataSrcAction$: Observable<string>;
  @Input() showLoadBusy$: Observable<string>;
  @Input() cacheBlockSizeIn: number;

  @Output() emitDataPuller = new EventEmitter<any>();


  columnDefs$: Observable<any[]>;

  gridOptions: GridOptions;
  gridParam: any;

  constructor() {
    console.log('InfiniteAgGridComponent loaded');
  }

  ngOnInit() {
    const infGridOptions = new InfiniteGridOptions(this.colHeaders$);
    this.columnDefs$ = infGridOptions.getColumnDefsObs();
    this.gridOptions = <GridOptions>infGridOptions;
    this.gridOptions.cacheBlockSize = this.cacheBlockSizeIn;
    this.gridOptions.onGridReady = (params) => { this.onGridReadyImpl(params); };
  }

  private onGridReadyImpl(params) {
    this.gridParam = params;
    this.gridParam.api.showLoadingOverlay();

    this.showLoadBusy$.subscribe(showBusyFlg => {
      if (showBusyFlg) {
        this.gridParam.api.showLoadingOverlay();
      }
    });

    this.dataSrcAction$.subscribe(action => {
      if ('reset' === action) {
        this.gridParam.api.showLoadingOverlay();
        console.log('  onGridReady reset');
        const gDataSrc = new InfiniteDataSource(this.emitDataPuller, this.rowDataRepo$, this.colHeaders$, this.gridParam.api);
        this.gridOptions.api.setDatasource(gDataSrc);
      } else if ('init' === action) {
        this.gridParam.api.showLoadingOverlay();
        console.log('  onGridReady init ');
        const custDS = new InfiniteDataSource(this.emitDataPuller, this.rowDataRepo$, this.colHeaders$, this.gridParam.api);
        this.gridOptions.api.setDatasource(custDS);
      }
    });

  }

  onBtExport() {
    console.log('acting onBtExport');
    const params = <IExportCsvParams>{
      fileName: 'report-transactions.xls'
    };
    // this.gridParam.api.exportDataAsExcel({});
    this.gridOptions.api.exportDataAsCsv(params);
  }
}

class InfiniteDataSource implements IDatasource {
  private headers = [];
  private dataRepo = [];
  constructor(private emitPullData: EventEmitter<any>,
    private rowDataObsrv: Observable<any[]>,
    private colHeadersObsrv: Observable<any[]>,
    private gParams) {
    console.log('InfiniteDataSource ');
    this.colHeadersObsrv.subscribe(head => this.headers = head);
    this.rowDataObsrv.subscribe(rows => this.dataRepo = rows);
  }

  rowCount: null;
  getRows(params: IGetRowsParams) {
    console.log(' paramsAPI ' + this.gParams);
    this.gParams.showLoadingOverlay();
    console.error('asking for ' + params.startRow + ' to ' + params.endRow);

    if (params.startRow !== 0) {
      console.error('emitPullData ');
      this.emitPullData.emit('');
    }

    let lastRow = -1;
    let processedRowData = [];
    // dataRepo.forEach( e => console.log(e.id));

    if (this.dataRepo && this.dataRepo.length > 0) {
      console.error('Current repo size: ' + this.dataRepo.length + ' ' + params.endRow + ' lastRow ' + lastRow);
      const newBlockData = this.dataRepo.slice(params.startRow, params.endRow);
      processedRowData = this.processRowData(newBlockData);
    }

    if (this.dataRepo.length <= params.endRow) {
      console.error(' lastRow set to ' + this.dataRepo.length);
      lastRow = this.dataRepo.length;
    } else {
      lastRow = -1;
    }

    params.successCallback(processedRowData, lastRow);

    if (!processedRowData || processedRowData.length === 0) {
      this.gParams.showNoRowsOverlay();
    } else {
      this.gParams.hideOverlay();
    }
  }

  private processRowData(rows: any[]): any[] {

    if (!rows || rows.length === 0) { return null; }
    console.log('private processRowData ' + rows.length);

    const rowData = [];
    for (const d of rows) {
      const newData = {};
      this.headers.forEach(h => Object.defineProperty(newData, h, { value: d[h] }));
      rowData.push(newData);
    }
    return rowData;
  }

}

class InfiniteGridOptions implements GridOptions {

  pagination = true;
  enableColResize = true;
  overlayLoadingTemplate = `<div align="center"><p>Loading...</p><img src="assets/loading-magic-cube.gif"> </div>`;
  overlayNoRowsTemplate = 'No Data to show';
  rowModelType = 'infinite';
  paginationAutoPageSize = true;

  private columnDefsSub = new BehaviorSubject<any>([]);
  private columnDefsObs = this.columnDefsSub.asObservable();


  constructor(private headersObs: Observable<any[]>) {
    console.log('InfiniteGridOptions ');
    this.getColumnDesfSrv();
  }

  getColumnDefsObs() {
    return this.columnDefsObs;
  }
  private async getColumnDesfSrv() {
    await this.headersObs.subscribe(headers => {
      console.log('getColumnDesfSrv header ' + headers);

      const columnHeaders = this.processColumnDefs(headers);
      console.log(' columnHeaders set obs');
      this.columnDefsSub.next(columnHeaders);
    });
  }
  private processColumnDefs(headers) {
    if (!headers) { return; }
    const columnDefs = [];
    headers.forEach(h => {
      columnDefs.push({
        headerName: h
        , field: h
        , width: 100
      });
    });
    return columnDefs;
  }
}

interface IExportCsvParams {

  skipHeader?: false; // Set to true if you don't want to first line to be the column header names.
  columnGroups?: false; // Set to true to include header column groupings.
  skipGroups?: true; // Set to true to skip row group headers and footers if grouping rows. No impact if not grouping rows.
  skipFooters?: true; // Set to true to skip footers only if grouping. No impact if not grouping or if not using footers in grouping.
  suppressQuotes?: true; // Set to true to not use double quotes between values.
  fileName?: string; // String to use as the file name. If missing, the file name 'export.csv' will be used.
  /*
  customHeader: If you want to put some text at the top of the csv file, stick it here. 
                You will need to include '\n' at the end, or many '\n' if you want the header to span lines.

                customFooter: Same as customHeader, but for the end of the file.

  allColumns: If true, all columns will be exported in the order they appear in columnDefs. 
              Otherwise only the columns currently showing the in grid, and in that order, are exported.

  onlySelected: Only export selected rows.
  onlySelectedAllPages: Only export selected rows including other pages (only makes sense when using pagination).
  columnSeparator: The column separator. Defaults to comma.
  columnKeys: Provide a list (an array) of column keys if you want to export specific columns.
  shouldRowBeSkipped: Allows you to skip entire rows from the export.
  processCellCallback: Allows you to process (typically format) cells for the CSV.
  processHeaderCallback: Allows you to create custom header values for the export.
   */
}