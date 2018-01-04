import { Component, OnInit } from '@angular/core';
import { }

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  title:string
  constructor() { }

  ngOnInit() {
    this.title = "Hi I m Here Pradhap"
  }

}
