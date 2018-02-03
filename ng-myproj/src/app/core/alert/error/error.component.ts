import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'alert-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})


export class ErrorComponent implements OnInit {

  @Input() errMsg;
  constructor() { }

  ngOnInit() {
  }

}
