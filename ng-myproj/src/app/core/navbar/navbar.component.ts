import { Component, OnInit } from '@angular/core';

import { UserService } from '../services/user/user.service';
import { User } from '@app/core';

import { Observable } from 'rxjs/Observable';
import { Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'core-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn: Observable<boolean>;
  userLoggedIn: Observable<string>;

  constructor(private userService: UserService) { }

  @Output() emitNavbarRouter: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit() {
    // this.isLoggedIn = this.userService.getLoggedinUser();
    // this.userLoggedIn = this.userService.getUserLoggedin().map(user => user.userName);
    this.isLoggedIn = this.userService.isUserSignedin();
    this.userLoggedIn = this.userService.getUserSignedin().map(user => user.userName);

  }

  navbarRouter(path: string) {
    console.log('path clicked ' + path);
    this.emitNavbarRouter.emit(path);
  }

}
