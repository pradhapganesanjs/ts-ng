import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

import { User } from './user.bo';

@Injectable()
export class UserService {
    userRes: any;

    constructor(private http: Http) {}

    loginUser(user: User) {
        console.log(`userName: ${user.userName}, userPassword: ${user.password}`);
        return this.http.get('https://public-api.wordpress.com/rest/v1.1/sites/oliverveits.wordpress.com/posts/3078')
        .map((userRes: Response) => userRes.json())
        .subscribe(data => {
                this.userRes = data;
                console.log(this.userRes);
        });
    }
}
