import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-input-mat',
  templateUrl: './input-mat.component.html',
  styleUrls: ['./input-mat.component.css']
})
export class InputMatComponent implements OnInit {

  @Input() placeHolderIn: string;
  @Input() disabledIn = false;
  @Input() inputval: string;

  @Output() InputEvt = new EventEmitter();

  isDisabled: boolean = this.disabledIn;
  placeHolder: string = this.placeHolderIn ? this.placeHolder : 'Enter a value';

  constructor() { }

  ngOnInit() {
  }

  onInputEvent(val) {
    this.InputEvt.emit(val);
  }
}
