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
  private loading = false;
  private model: any = {};

  @Output() emitLoginResult = new EventEmitter<Map<string, string>>();

  loginStatusMap: Map<string, string>;

  constructor(private userBo: User,
    private userService: UserService) {
    console.log('LoginFormComponent Construtor'
      + this.userBo + this.userService);
  }

  ngOnInit() { }

  signinUser() {
    console.log(`signinUser form ${this.model.username}, ${this.model.password}`);

    this.userService.signinUser(this.model.username, this.model.password);
    this.userService.isUserSignedin()
                    .subscribe(bool => {
                          if (bool) {
                            this.handleSuccess();
                          } else {
                            this.handleFail('Login Failed');
                          } });
  }

  login() {
    console.log(`login params ${this.model.username}, ${this.model.password}`);
    this.loading = true;
    console.log('userBo ' + this.userBo);
    this.userBo.userName = this.model.username;
    this.userBo.password = this.model.password;

    this.userService.loginUser(this.userBo)
      .subscribe(data => this.handleRes(data),
      err => this.handleFail(err));
  }
  handleRes(data) {
    console.log('i will handle the resp ' + data.response['error_code']);
    if ('0' === data.response['error_code']) {
      this.userService.login(true);
      this.userService.loggedinUser(this.userBo);
      this.handleSuccess();
    } else {
      this.loginStatusMap.set('error_msg', data.response['error_msg']);
      this.emitLoginResult.emit(this.loginStatusMap);
      this.userService.login(false);
    }
  }
  private handleSuccess() {
    this.loginStatusMap = new Map<string, string>();
    console.log('login success ');
    this.loginStatusMap.set('status', 'success');
    console.log('login success ');
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
