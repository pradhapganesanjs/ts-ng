import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';

import { User } from './user.bo';
import { BaseService } from '../base/base.service';
import { HttpReq } from '../helpers/http-util';

@Injectable()
export class UserService extends BaseService {
    userRes: any;
    loggedIn: boolean;
    private userPost: any = {};

    constructor(private httpSub: Http) {
        super(httpSub);
        console.log('UserService Construtor'
        + this.httpSub);
    }

    loginUser(user: User) {
        this.loggedIn = false;

        const userHttpGet: HttpReq = new HttpReq('/api/login');
        userHttpGet.params.append('username', user.userName);
        userHttpGet.params.append('password', user.password);

        return super.httpGet(userHttpGet);
    }
    /*fetchUser(user: User) {
        console.log(`userName: ${user.userName}, userPassword: ${user.password}`);
        return this.http.get('https://public-api.wordpress.com/rest/v1.1/sites/oliverveits.wordpress.com/posts/3078')
        .map((userRes: Response) => userRes.json())
        .subscribe(data => {
                this.userRes = data;
                console.log(this.userRes);
        });
    }*/

}
