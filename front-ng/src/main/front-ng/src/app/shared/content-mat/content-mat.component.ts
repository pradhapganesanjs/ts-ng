import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'content-mat',
  templateUrl: './content-mat.component.html',
  styleUrls: ['./content-mat.component.css']
})
export class ContentMatComponent implements OnInit {

  @Input() title;
  @Input() subtitle;
  @Input() content;

  constructor() { }

  ngOnInit() {
  }

}
