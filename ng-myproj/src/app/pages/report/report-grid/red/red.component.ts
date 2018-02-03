import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-red',
  template: '<span style="color: red"></span>',
  styleUrls: ['./red.component.css']
})
export class RedComponent implements OnInit {

  private params: any;

  constructor() { }

  ngOnInit() {
  }

  agInit(params: any): void {
      this.params = params;
  }

}
