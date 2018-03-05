import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionProcessor} from '@app/core';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  userLoggedIn = false;

  constructor(private router: Router, private sessPros: SessionProcessor) { }

  ngOnInit() {
    this.sessPros.isUserSignedin().subscribe(lgflg => this.userLoggedIn = lgflg);
    console.log('this.userService.loggedIn  ' + this.userLoggedIn);
    if (!this.userLoggedIn) {
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/logindashboard']);
    }
  }

  handleNavbarRouter(path: string) {
    console.log(' handleNavbarRouter ' + path);
    const routArr: string[] = [];
    routArr.push(path);
    this.router.navigate(routArr);
  }
}
