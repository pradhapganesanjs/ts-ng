import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '@app/core';

@Component({
  selector: 'pages-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title: string;

  passLoginResul: string;
  isSuccess: boolean;

constructor(private router: Router, private alrtSrv: AlertService) { }

  ngOnInit() {

  }

  receiveLoginStatus(status: Map<string, string>) {
    this.clearVals();
    console.log('status updated ' + status);
    if ( status.get('status') === 'success') {
      this.isSuccess = true;
      this.router.navigate(['/logindashboard']);
    } else {
      this.handleErr( status.get('error_msg') );
    }
  }

  clearVals() {
    this.isSuccess = null;
  }


  private handleErr(err) {
    this.alrtSrv.showAlertErrMsg(err);
  }

}
