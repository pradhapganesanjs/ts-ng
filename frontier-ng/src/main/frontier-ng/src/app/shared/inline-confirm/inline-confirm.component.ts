import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'inline-confirm',
  templateUrl: './inline-confirm.component.html',
  styleUrls: ['./inline-confirm.component.css']
})
export class InlineConfirmComponent implements OnInit {

  @Input() title;
  @Input() subtitle;
  @Input() content;
  @Input() btn_lab_confirm;
  @Input() btn_lab_cancel;
  @Output() confirmEmitter = new EventEmitter<string>();
  @Output() cancelEmitter = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }
  emitConfirm(confirmMsg: string) {
    this.confirmEmitter.emit(confirmMsg);
  }
  emitCancel(cancelMsg: string) {
    this.cancelEmitter.emit(cancelMsg);
  }
}
