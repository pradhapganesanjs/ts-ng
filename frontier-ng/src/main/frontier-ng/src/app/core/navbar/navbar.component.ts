import { Component, OnInit } from '@angular/core';

import { User } from '../services/user/user.bo';
import { SessionProcessor } from '../services/helpers/session-processor';

import { Observable } from 'rxjs/Observable';
import { Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'core-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  userLoggedIn = '';

  constructor(private sessPros: SessionProcessor) { }

  @Output() emitNavbarRouter: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit() {
    this.sessPros.isUserSignedin().subscribe(flg => this.isLoggedIn = flg);
    this.sessPros.getUserSignedin()
                    .subscribe(user => user ? this.userLoggedIn = user.userName : '');

  }

  navbarRouter(path: string) {
    console.log('path clicked ' + path);
    this.emitNavbarRouter.emit(path);
  }

}
