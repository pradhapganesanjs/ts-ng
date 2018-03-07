import { Component, OnInit } from '@angular/core';
import { UserService } from '@app/core';
import { Router } from '@angular/router';

@Component({
  selector: 'logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private userSrv: UserService, private router: Router) { }

  ngOnInit() {
  }

  handleConfirm(confirm: string) {
    this.userSrv.signOut();
    this.router.navigate(['/home']);
  }
  handleCancel(cancel: string) {
    this.router.navigate(['/logindashboard']);
  }
}
