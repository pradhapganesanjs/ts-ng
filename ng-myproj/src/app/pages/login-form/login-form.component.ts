import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../services/user/user.bo';

@Component({
  selector: 'pages-login-form',
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
    this.loginStatusMap = new Map<string, string>();
    this.loginStatusMap.set('status', 'fail');
    this.loginStatusMap.set('error_msg', '');

    if ('0' === data.response['error_code']) {
      this.loginStatusMap.set('status', 'success');
      this.emitLoginResult.emit(this.loginStatusMap);
    } else {
      this.loginStatusMap.set('error_msg', data.response['error_msg']);
      this.emitLoginResult.emit(this.loginStatusMap);
    }
  }
  handleFail(err) {
    console.log('i will handle FAIL' + err);
    this.loginStatusMap.set('error_msg', err);
    this.emitLoginResult.emit(this.loginStatusMap);
  }
}
