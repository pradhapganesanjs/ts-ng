import { Component, OnInit } from '@angular/core';
import { Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-select-mat',
  templateUrl: './select-mat.component.html',
  styleUrls: ['./select-mat.component.css']
})
export class SelectMatComponent implements OnInit {

  @Input() recordColumns: any[];
  @Output() selectedColumn = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  onChange(selected) {
    this.selectedColumn.emit(selected.value);
  }

}
