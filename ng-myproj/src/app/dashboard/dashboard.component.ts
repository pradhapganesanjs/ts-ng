import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService, } from '@app/core';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  userLoggedIn = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    //this.userService.getLoggedinUser().map(lgflg => this.userLoggedIn = lgflg);
    this.userService.isUserSignedin().map(lgflg => this.userLoggedIn = lgflg);
    console.log('this.userService.loggedIn  ' + this.userLoggedIn);
    if (this.userLoggedIn) {
      this.router.navigate(['/logindashboard']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  handleNavbarRouter(path: string) {
    console.log(' handleNavbarRouter ' + path);
    const routArr: string[] = [];
    routArr.push(path);
    this.router.navigate(routArr);
  }
}
