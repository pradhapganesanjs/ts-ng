import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'pages-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title: string;

  passLoginResul: string;

  errorMsg: String;
  isSuccess: boolean;

  constructor(private router: Router) { }

  ngOnInit() {
    this.title = 'Hi I m Here Pradhap';
  }

  receiveLoginStatus(status: Map<string, string>) {
    this.clearVals();
    console.log('status updated ' + status);
    if ( status.get('status') === 'success') {
      this.isSuccess = true;
      this.router.navigate(['/logindashboard']);
    } else {
      this.errorMsg = status.get('error_msg');
    }
  }

  clearVals() {
    this.errorMsg = null;
    this.isSuccess = null;
  }
}
