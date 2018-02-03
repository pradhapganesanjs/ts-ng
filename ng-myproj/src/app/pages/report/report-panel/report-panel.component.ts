import { Component, OnInit } from '@angular/core';
import { SCRIPTS as script } from '../view-scripts';
import { ReportCriteriaObj, ReportCriteria } from '../services/report-criteria.model';
import { ReportService } from '../services/report.service';
import { Observable } from 'rxjs/Observable';
import { Report } from '../services/report.bo';

@Component({
  selector: 'report-panel',
  templateUrl: './report-panel.component.html',
  styleUrls: ['./report-panel.component.css']
})
export class ReportPanelComponent implements OnInit {

  recordColumns: any[];

  isEditableInput: boolean;
  selectedColKey: string;

  isReadyToSubmit: boolean;

  // form values
  private selectedColVal = '';
  private enteredInputVal = '';

  private recordObserve: Observable<Report[]>;
  resultReports: Report[];

  placeHolder = 'Enter a criteria';

  constructor(private reportService: ReportService) {
  }

  ngOnInit() {
    this.isEditableInput = false;
    this.isReadyToSubmit = false;

    const repCrits: string[] = Object.getOwnPropertyNames(ReportCriteriaObj);
    const recColumnTmp = [];
    repCrits.forEach(r => recColumnTmp.push({ 'value': r, 'viewValue': script[r] }));
    this.recordColumns = recColumnTmp;

    this.selectedColVal = '';
    this.reportService.reports().subscribe(reps => {
      this.resultReports = reps;
    });
  }

  fetchClickAction() {
    console.log(`ready to submit key : ${this.selectedColKey} val: ${this.enteredInputVal}`);
    const repCrit: ReportCriteria = {};
    repCrit[this.selectedColKey] = this.enteredInputVal;
    this.reportService.reportByCriteria(repCrit);
  }

  onSelectedColumn(selected) {
    console.log('Outter Commponent ' + selected);
    this.selectedColKey = selected;
    if (selected && '' !== selected) {
      this.isEditableInput = true;
    }
  }

  handleInput(inputBox) {
    console.log(inputBox);
    if (inputBox && '' !== inputBox) {
      this.isReadyToSubmit = true;
      this.enteredInputVal = inputBox;
    } else {
      this.isReadyToSubmit = false;
    }
  }
}
