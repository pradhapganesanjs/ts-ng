import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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

  constructor(private router: Router, private userBo: User, private userService: UserService ) { }

  ngOnInit() {}
  login() {
    console.log(`login success ${this.model.username}, ${this.model.password}`);
    this.loading = true;
    console.log('userBo ' + this.userBo);
    this.userBo.userName = this.model.username;
    this.userBo.password = this.model.password;

    const userServProm = function(succ, fail){
      const resServ: any = this.userService.loginUser(this.userBo);
      console.log('resServ ' + resServ);

      if (resServ && 'failed' === resServ.toString()) {
        console.log('failed');
        fail('failed');
      } else {
        succ(resServ);
      }
    };
    const handleRes = function(data){
      console.log('data ' + data);
      this.router.navigate(['/dashboard']);
    };
    const handleFail = function(data){
      this.router.navigate(['/']);
    };

    const prom = new Promise(userServProm)
                    .then(handleRes)
                    .catch(handleFail);

  }
}
