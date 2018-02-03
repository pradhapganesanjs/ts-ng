webpackJsonp(["report.module"],{

/***/ "../../../../../src/app/pages/report/report-container/report-container.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/pages/report/report-container/report-container.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <report-panel></report-panel>\n    <report-grid></report-grid>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/pages/report/report-container/report-container.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportContainerComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ReportContainerComponent = (function () {
    function ReportContainerComponent() {
    }
    ReportContainerComponent.prototype.ngOnInit = function () {
    };
    ReportContainerComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-report-container',
            template: __webpack_require__("../../../../../src/app/pages/report/report-container/report-container.component.html"),
            styles: [__webpack_require__("../../../../../src/app/pages/report/report-container/report-container.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ReportContainerComponent);
    return ReportContainerComponent;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/report-grid/report-grid.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".mat-table {\r\n    overflow: auto;\r\n    max-height: 500px;\r\n  }\r\n  \r\n  .mat-header-cell.mat-sort-header-sorted {\r\n    color: black;\r\n  }\r\n  ", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/pages/report/report-grid/report-grid.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n\n  <mat-paginator #paginator [pageSize]=\"20\" [pageSizeOptions]=\"[5, 10, 25, 100]\">\n  </mat-paginator>\n\n  <mat-table #table [dataSource]=\"reportDataSrc\" matSort>\n    <ng-container matColumnDef=\"_id\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>ID</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.id}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_flow\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>FLOW</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.flow}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_sourceId\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>SOURCE_ID</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.sourceId}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_sourceUId\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>SOURCE_UID</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.sourceUId}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_sourceSystem\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>SOURCE_SYS</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.sourceSystem}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_sourceVersion\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>SOURCE_V</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.sourceVersion}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_rdsEligible\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>RDS_ELIGIBLE</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.rdsEligible}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_stream\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>STREAM</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.stream}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_receivedTs\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>RECEIVED_TS</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.receivedTs}}</mat-cell>\n    </ng-container>\n\n    <ng-container matColumnDef=\"_regReportingRef\">\n      <mat-header-cell *matHeaderCellDef mat-sort-header>REPORT_REF</mat-header-cell>\n      <mat-cell *matCellDef=\"let rec\">{{rec.regReportingRef}}</mat-cell>\n    </ng-container>\n\n\n    <mat-header-row *matHeaderRowDef=\"displayColumns\"></mat-header-row>\n    <mat-row *matRowDef=\"let row; columns: displayColumns\"></mat-row>\n\n  </mat-table>\n\n</div>"

/***/ }),

/***/ "../../../../../src/app/pages/report/report-grid/report-grid.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportGridComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_report_service__ = __webpack_require__("../../../../../src/app/pages/report/services/report.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_report_bo__ = __webpack_require__("../../../../../src/app/pages/report/services/report.bo.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


// import { ReportService, Report } from '@app/pages/report'; //../../services/report/report.service';


// import { Report } from '../../services/report/report.bo';
var ReportGridComponent = (function () {
    function ReportGridComponent(reportService) {
        this.reportService = reportService;
        this.displayColumns = [];
    }
    // ngDoCheck() {
    //   console.log("ngDoCheck");
    //   this.reportService.reports()
    //   .subscribe( reps => this.handleResp(reps)
    //              , err => this.handleErr(err));
    // }
    ReportGridComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.reportDataSrc = new __WEBPACK_IMPORTED_MODULE_1__angular_material__["j" /* MatTableDataSource */]();
        this.reportService.getReports().subscribe(function (data) { return _this.handleResp(data); }, function (err) { return _this.handleErr(err); });
        /*
         this.reportService.fetchReportsAll();
        this.reportService.reports()
                          .subscribe( reps => this.handleResp(reps)
                                     , err => this.handleErr(err));
                                     */
        var prop = Object.getOwnPropertyNames(new __WEBPACK_IMPORTED_MODULE_3__services_report_bo__["a" /* Report */]());
        var propAsColumns = [];
        for (var _i = 0, prop_1 = prop; _i < prop_1.length; _i++) {
            var p = prop_1[_i];
            propAsColumns.push(p);
        }
        console.log(propAsColumns);
        this.displayColumns = propAsColumns;
        console.log(' ' + this.reportService.reportByCriteria({ id: 'ID_10' }));
    };
    /**
     * Set the sort after the view init since this component will
     * be able to query its view for the initialized sort.
     */
    ReportGridComponent.prototype.ngAfterViewInit = function () {
        this.reportDataSrc.sort = this.sort;
        this.reportDataSrc.paginator = this.paginator;
    };
    ReportGridComponent.prototype.handleResp = function (reports) {
        console.log('handleresp : ' + reports);
        var rows = new Array();
        // this.recordsList = reports;
        // this.reportDataSrc = new MatTableDataSource<Report>(reports);
        this.reportDataSrc.data = reports;
    };
    ReportGridComponent.prototype.handleErr = function (err) {
        console.log('err ' + err);
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_12" /* ViewChild */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["h" /* MatSort */]),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_1__angular_material__["h" /* MatSort */])
    ], ReportGridComponent.prototype, "sort", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_12" /* ViewChild */])(__WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatPaginator */]),
        __metadata("design:type", __WEBPACK_IMPORTED_MODULE_1__angular_material__["e" /* MatPaginator */])
    ], ReportGridComponent.prototype, "paginator", void 0);
    ReportGridComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'report-grid',
            template: __webpack_require__("../../../../../src/app/pages/report/report-grid/report-grid.component.html"),
            styles: [__webpack_require__("../../../../../src/app/pages/report/report-grid/report-grid.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__services_report_service__["a" /* ReportService */]])
    ], ReportGridComponent);
    return ReportGridComponent;
}());

/*
export class ReportDataSource extends DataSource<Report> {

  constructor(private reportService: ReportService) {

  }

  connect() {
    new ReportGridComponent(this.reportService).
  }

}
*/


/***/ }),

/***/ "../../../../../src/app/pages/report/report-panel/report-panel.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".panel-form{\r\n    margin: 30px;\r\n}\r\n.button-mat{\r\n    padding-top: 10px;\r\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/pages/report/report-panel/report-panel.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"panel-form\">\n    <form class=\"\">\n        <div class=\"row\">\n            <div class=\"col-md-3 select-mat\">\n                <app-select-mat [recordColumns]=\"recordColumns\" (selectedColumn)=\"onSelectedColumn($event)\"></app-select-mat>\n            </div>\n\n            <div class=\"col-md-3 input-mat\">\n                <app-input-mat [placeHolderIn]=\"placeHolder\" [disabledIn]=\"isEditableInput\" (InputEvt)=\"handleInput($event)\"></app-input-mat>\n            </div>\n            <div class=\"col-md-2 button-mat\">\n                <button type=\"button\" class=\"btn btn-primary\" mat-raised-button [disabled]=\"!isReadyToSubmit\" (click)=\"fetchClickAction()\">Fetch</button>\n            </div>\n        </div>\n    </form>\n</div>\n<div>\n        <ul>\n            <li *ngFor=\"let rec of resultReports\">\n                id : {{rec ? rec.id : '-na-'}}\n                flow : {{rec ? rec.flow : '-na-' }}\n            </li>\n        </ul>\n</div>"

/***/ }),

/***/ "../../../../../src/app/pages/report/report-panel/report-panel.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportPanelComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__view_scripts__ = __webpack_require__("../../../../../src/app/pages/report/view-scripts.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__services_report_criteria_model__ = __webpack_require__("../../../../../src/app/pages/report/services/report-criteria.model.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__services_report_service__ = __webpack_require__("../../../../../src/app/pages/report/services/report.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ReportPanelComponent = (function () {
    function ReportPanelComponent(reportService) {
        this.reportService = reportService;
        // form values
        this.selectedColVal = '';
        this.enteredInputVal = '';
        this.placeHolder = 'Enter a criteria';
    }
    ReportPanelComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.isEditableInput = false;
        this.isReadyToSubmit = false;
        var repCrits = Object.getOwnPropertyNames(__WEBPACK_IMPORTED_MODULE_2__services_report_criteria_model__["a" /* ReportCriteriaObj */]);
        var recColumnTmp = [];
        repCrits.forEach(function (r) { return recColumnTmp.push({ 'value': r, 'viewValue': __WEBPACK_IMPORTED_MODULE_1__view_scripts__["a" /* SCRIPTS */][r] }); });
        this.recordColumns = recColumnTmp;
        this.selectedColVal = '';
        this.reportService.reports().subscribe(function (reps) {
            _this.resultReports = reps;
        });
    };
    ReportPanelComponent.prototype.fetchClickAction = function () {
        console.log("ready to submit key : " + this.selectedColKey + " val: " + this.enteredInputVal);
        var repCrit = {};
        repCrit[this.selectedColKey] = this.enteredInputVal;
        this.reportService.reportByCriteria(repCrit);
    };
    ReportPanelComponent.prototype.onSelectedColumn = function (selected) {
        console.log('Outter Commponent ' + selected);
        this.selectedColKey = selected;
        if (selected && '' !== selected) {
            this.isEditableInput = true;
        }
    };
    ReportPanelComponent.prototype.handleInput = function (inputBox) {
        console.log(inputBox);
        if (inputBox && '' !== inputBox) {
            this.isReadyToSubmit = true;
            this.enteredInputVal = inputBox;
        }
        else {
            this.isReadyToSubmit = false;
        }
    };
    ReportPanelComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'report-panel',
            template: __webpack_require__("../../../../../src/app/pages/report/report-panel/report-panel.component.html"),
            styles: [__webpack_require__("../../../../../src/app/pages/report/report-panel/report-panel.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__services_report_service__["a" /* ReportService */]])
    ], ReportPanelComponent);
    return ReportPanelComponent;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/report-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__report_container_report_container_component__ = __webpack_require__("../../../../../src/app/pages/report/report-container/report-container.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__report_grid_report_grid_component__ = __webpack_require__("../../../../../src/app/pages/report/report-grid/report-grid.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var routes = [
    { path: '', redirectTo: 'report', pathMatch: 'full' },
    { path: 'report', component: __WEBPACK_IMPORTED_MODULE_3__report_grid_report_grid_component__["a" /* ReportGridComponent */] },
    { path: 'panel', component: __WEBPACK_IMPORTED_MODULE_2__report_container_report_container_component__["a" /* ReportContainerComponent */] }
];
var ReportRoutingModule = (function () {
    function ReportRoutingModule() {
    }
    ReportRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* RouterModule */].forChild(routes)],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* RouterModule */]]
        })
    ], ReportRoutingModule);
    return ReportRoutingModule;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/report.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReportModule", function() { return ReportModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common__ = __webpack_require__("../../../common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_material__ = __webpack_require__("../../../material/esm5/material.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__report_grid_report_grid_component__ = __webpack_require__("../../../../../src/app/pages/report/report-grid/report-grid.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__report_container_report_container_component__ = __webpack_require__("../../../../../src/app/pages/report/report-container/report-container.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__report_panel_report_panel_component__ = __webpack_require__("../../../../../src/app/pages/report/report-panel/report-panel.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__report_routing_module__ = __webpack_require__("../../../../../src/app/pages/report/report-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__app_shared__ = __webpack_require__("../../../../../src/app/shared/index.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__services_report_service__ = __webpack_require__("../../../../../src/app/pages/report/services/report.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




//import {BrowserAnimationsModule} from '@angular/platform-browser/animations';



// import { ReportContainerComponent, ReportGridComponent, ReportPanelComponent } from './index';


// import { ReportPanelComponent } from './index';


var ReportModule = (function () {
    function ReportModule() {
    }
    ReportModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_common__["b" /* CommonModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_http__["c" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_common_http__["b" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_8__report_routing_module__["a" /* ReportRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_material__["f" /* MatPaginatorModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_material__["g" /* MatSelectModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_material__["i" /* MatSortModule */],
                __WEBPACK_IMPORTED_MODULE_4__angular_material__["k" /* MatTableModule */],
                __WEBPACK_IMPORTED_MODULE_9__app_shared__["a" /* SharedModule */]
            ],
            declarations: [__WEBPACK_IMPORTED_MODULE_6__report_container_report_container_component__["a" /* ReportContainerComponent */], __WEBPACK_IMPORTED_MODULE_5__report_grid_report_grid_component__["a" /* ReportGridComponent */], __WEBPACK_IMPORTED_MODULE_7__report_panel_report_panel_component__["a" /* ReportPanelComponent */]],
            exports: [
                __WEBPACK_IMPORTED_MODULE_6__report_container_report_container_component__["a" /* ReportContainerComponent */], __WEBPACK_IMPORTED_MODULE_5__report_grid_report_grid_component__["a" /* ReportGridComponent */], __WEBPACK_IMPORTED_MODULE_7__report_panel_report_panel_component__["a" /* ReportPanelComponent */]
            ],
            providers: [
                __WEBPACK_IMPORTED_MODULE_10__services_report_service__["a" /* ReportService */]
            ]
        })
    ], ReportModule);
    return ReportModule;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/services/report-criteria.model.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportCriteriaObj; });
/*
export class ReportCriteria {
    private _id: string;
    private _status: string;
    private _flow: string;
    private _sourceId: string;
    private _sourceUId: string;
    private _sourceSystem: string;
    private _sourceVersion: string;
    private _rdsEligible: boolean;
    private _stream: string;
    private _receivedTs: string;
    private _regReportingRef: string;

    constructor() {

    }


    public get id(): string {
        return this._id;
    }

    public set id(value: string) {
        this._id = value;
    }

    public get status(): string {
        return this._status;
    }

    public set status(value: string) {
        this._status = value;
    }

    public get flow(): string {
        return this._flow;
    }

    public set flow(value: string) {
        this._flow = value;
    }

    public get sourceId(): string {
        return this._sourceId;
    }

    public set sourceId(value: string) {
        this._sourceId = value;
    }

    public get sourceUId(): string {
        return this._sourceUId;
    }

    public set sourceUId(value: string) {
        this._sourceUId = value;
    }

    public get sourceSystem(): string {
        return this._sourceSystem;
    }

    public set sourceSystem(value: string) {
        this._sourceSystem = value;
    }

    public get sourceVersion(): string {
        return this._sourceVersion;
    }

    public set sourceVersion(value: string) {
        this._sourceVersion = value;
    }

    public get rdsEligible(): boolean {
        return this._rdsEligible;
    }

    public set rdsEligible(value: boolean) {
        this._rdsEligible = value;
    }

    public get stream(): string {
        return this._stream;
    }

    public set stream(value: string) {
        this._stream = value;
    }

    public get receivedTs(): string {
        return this._receivedTs;
    }

    public set receivedTs(value: string) {
        this._receivedTs = value;
    }

    public get regReportingRef(): string {
        return this._regReportingRef;
    }

    public set regReportingRef(value: string) {
        this._regReportingRef = value;
    }
}
*/
var ReportCriteriaObj = {
    id: '',
    status: '',
    flow: '',
    sourceId: '',
    sourceUId: '',
    sourceSystem: '',
    sourceVersion: '',
    rdsEligible: false,
    stream: '',
    receivedTs: '',
    regReportingRef: ''
};


/***/ }),

/***/ "../../../../../src/app/pages/report/services/report.bo.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Report; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__ = __webpack_require__("../../../../json-typescript-mapper/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var Report = (function () {
    function Report() {
        this._id = undefined;
        this._flow = undefined;
        this._sourceId = undefined;
        this._sourceUId = undefined;
        this._sourceSystem = undefined;
        this._sourceVersion = undefined;
        this._rdsEligible = undefined;
        this._stream = undefined;
        this._receivedTs = undefined;
        this._regReportingRef = undefined;
    }
    Object.defineProperty(Report.prototype, "id", {
        get: function () {
            return this._id;
        },
        set: function (value) {
            this._id = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "status", {
        get: function () {
            return this._status;
        },
        set: function (value) {
            this._status = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "stream", {
        get: function () {
            return this._stream;
        },
        set: function (value) {
            this._stream = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "flow", {
        get: function () {
            return this._flow;
        },
        set: function (value) {
            this._flow = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "sourceSystem", {
        get: function () {
            return this._sourceSystem;
        },
        set: function (value) {
            this._sourceSystem = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "sourceId", {
        get: function () {
            return this._sourceId;
        },
        set: function (value) {
            this._sourceId = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "sourceUId", {
        get: function () {
            return this._sourceUId;
        },
        set: function (value) {
            this._sourceUId = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "sourceStatus", {
        get: function () {
            return this._sourceStatus;
        },
        set: function (value) {
            this._sourceStatus = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "sourceVersion", {
        get: function () {
            return this._sourceVersion;
        },
        set: function (value) {
            this._sourceVersion = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "regReportingRef", {
        get: function () {
            return this._regReportingRef;
        },
        set: function (value) {
            this._regReportingRef = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "rdsEligible", {
        get: function () {
            return this._rdsEligible;
        },
        set: function (value) {
            this._rdsEligible = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(Report.prototype, "receivedTs", {
        get: function () {
            return this._receivedTs;
        },
        set: function (value) {
            this._receivedTs = value;
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('id'),
        __metadata("design:type", String)
    ], Report.prototype, "_id", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('status'),
        __metadata("design:type", String)
    ], Report.prototype, "_status", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('sourceStatus'),
        __metadata("design:type", String)
    ], Report.prototype, "_sourceStatus", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('sourceVersion'),
        __metadata("design:type", String)
    ], Report.prototype, "_sourceVersion", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('regReportingRef'),
        __metadata("design:type", String)
    ], Report.prototype, "_regReportingRef", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('rdsEligible'),
        __metadata("design:type", Boolean)
    ], Report.prototype, "_rdsEligible", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('receivedTs'),
        __metadata("design:type", Number)
    ], Report.prototype, "_receivedTs", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('stream'),
        __metadata("design:type", String)
    ], Report.prototype, "_stream", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('flow'),
        __metadata("design:type", String)
    ], Report.prototype, "_flow", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('sourceSystem'),
        __metadata("design:type", String)
    ], Report.prototype, "_sourceSystem", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('sourceId'),
        __metadata("design:type", String)
    ], Report.prototype, "_sourceId", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0_json_typescript_mapper__["JsonProperty"])('sourceUId'),
        __metadata("design:type", String)
    ], Report.prototype, "_sourceUId", void 0);
    return Report;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/services/report.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_json_typescript_mapper__ = __webpack_require__("../../../../json-typescript-mapper/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_json_typescript_mapper___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_json_typescript_mapper__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__report_bo__ = __webpack_require__("../../../../../src/app/pages/report/services/report.bo.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_BehaviorSubject__ = __webpack_require__("../../../../rxjs/_esm5/BehaviorSubject.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var ReportService = (function () {
    function ReportService(httpSub, httpClient) {
        this.httpSub = httpSub;
        this.httpClient = httpClient;
        this.reportListSub = new __WEBPACK_IMPORTED_MODULE_6_rxjs_BehaviorSubject__["a" /* BehaviorSubject */](undefined);
        console.log('ReportService Construtor'
            + this.httpSub);
    }
    ReportService.prototype.getReports = function () {
        var _this = this;
        return this.httpSub.get('/api/reports').map(function (data) {
            if (!data) {
                throw new Error('something went wrong');
            }
            var recordList = [];
            for (var _i = 0, _a = data.json(); _i < _a.length; _i++) {
                var d = _a[_i];
                var desr = Object(__WEBPACK_IMPORTED_MODULE_3_json_typescript_mapper__["deserialize"])(__WEBPACK_IMPORTED_MODULE_4__report_bo__["a" /* Report */], d);
                recordList.push(desr);
            }
            _this.reportListSub.next(recordList);
            // const reportRes = deserialize(Report, data['result']);
            return recordList;
        });
    };
    ReportService.prototype.fetchReportsAll = function () {
        var _this = this;
        return this.httpSub.get('/api/reports').subscribe(function (data) {
            if (!data) {
                throw new Error('something went wrong');
            }
            var recordList = [];
            for (var _i = 0, _a = data.json(); _i < _a.length; _i++) {
                var d = _a[_i];
                var desr = Object(__WEBPACK_IMPORTED_MODULE_3_json_typescript_mapper__["deserialize"])(__WEBPACK_IMPORTED_MODULE_4__report_bo__["a" /* Report */], d);
                recordList.push(desr);
            }
            _this.reportListSub.next(recordList);
            // const reportRes = deserialize(Report, data['result']);
            return recordList;
        });
    };
    ReportService.prototype.reports = function () {
        return this.reportListSub.asObservable();
    };
    ReportService.prototype.reportByCriteria = function (repCrit) {
        var _this = this;
        console.log('repCrit ' + repCrit);
        if (repCrit) {
            console.log('repCrit status : ' + repCrit.status);
            console.log('repCrit id : ' + repCrit.id);
        }
        var headerParams = new __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["c" /* HttpHeaders */]();
        headerParams.set('Content-Type', 'application/json');
        this.httpClient.post('/api/reportsbycrit', repCrit, { headers: headerParams }).subscribe(function (res) {
            console.log('res ' + res);
            _this.reportListSub.next(res);
            _this.glRecordList = res;
        });
    };
    ReportService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["C" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */], __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["a" /* HttpClient */]])
    ], ReportService);
    return ReportService;
}());



/***/ }),

/***/ "../../../../../src/app/pages/report/view-scripts.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SCRIPTS; });
var SCRIPTS = {
    id: 'ID',
    status: 'Status',
    flow: 'Flow',
    sourceId: 'SourceID',
    sourceUId: 'Source UID',
    sourceSystem: 'SourceSystem',
    sourceVersion: 'SourceVersion',
    rdsEligible: 'RDSEligible',
    stream: 'Stream',
    receivedTs: 'ReceivedTimestamp',
    regReportingRef: 'ReportingRef'
};


/***/ })

});
//# sourceMappingURL=report.module.chunk.js.map