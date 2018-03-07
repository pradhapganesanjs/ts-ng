import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '@app/core';
import { User } from '@app/core';

import 'rxjs/add/operator/map';

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loading = false;
  model: any = {};

  @Output() emitLoginResult = new EventEmitter<Map<string, string>>();

  loginStatusMap: Map<string, string>;

  constructor(private userBo: User,
    private userService: UserService) {
    console.log('LoginFormComponent Construtor'
      + this.userBo + this.userService);
  }

  ngOnInit() { }

  signinUser() {
    console.log(`signinUser form ${this.model.username}`);

    this.userService.signinUser(this.model.username, this.model.password)
                    .subscribe(bool => {
                          if (bool) {
                            this.handleSuccess();
                          } else {
                            this.handleFail('Login Failed');
                          } },
                        err => this.handleFail(err));
  }

  private handleSuccess() {
    this.loginStatusMap = new Map<string, string>();
    console.log('login success ');
    this.loginStatusMap.set('status', 'success');

    this.emitLoginResult.emit(this.loginStatusMap);
  }
  private handleFail(err) {
    this.loginStatusMap = new Map<string, string>();
    console.log('i will handle FAIL' + err);
    this.loginStatusMap.set('error_msg', err);
    this.loginStatusMap.set('status', 'fail');
    this.emitLoginResult.emit(this.loginStatusMap);
  }
}
