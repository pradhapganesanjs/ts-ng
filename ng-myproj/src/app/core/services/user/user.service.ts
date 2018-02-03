import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { deserialize } from 'json-typescript-mapper';

import { User } from './user.bo';
import { UserDetails } from './userDetails.bo';
import { BaseService } from '../base/base.service';
import { HttpReq } from '../helpers/http-util';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable()
export class UserService extends BaseService {

    userRes: any;
    loggedIn: boolean;

    private isLoggedinSub = new BehaviorSubject<boolean>(false);
    private userLoggedinSub = new BehaviorSubject<User>(undefined);

    private userSignedinSub = new BehaviorSubject<UserDetails>(undefined);
    private isUserSignedinSub = new BehaviorSubject<boolean>(false);

    private userPost: any = {};

    private headerParamObj;

    constructor(private httpSub: Http, private httpClient: HttpClient) {
        super(httpSub);
        console.log('UserService Construtor' + this.httpSub);

        const headerParams = new HttpHeaders();
        headerParams.set('Content-Type', 'application/json');
        this.headerParamObj = { headers: headerParams };
    }

    signinUser(userName: string, password: string) {
        console.log(`userName : ${userName} pass: ${password}`);

        this.httpClient.post('/api/signin', { userName, password }, this.headerParamObj)
            /*.shareReplay()
            .do(res => this.handleResSigninUser(res)
                , err => this.setSignedOut()); */
            .subscribe(res => this.handleResSigninUser(res)
                        , err => this.setSignedOut());
    }
    private handleResSigninUser(res) {
        let userDtls = null;
        if (res) {
            userDtls = deserialize(UserDetails, res);
            console.log(' userDtls ' + userDtls.toString());
            // this.userSignedinSub.next(userDtls);
            // this.isUserSignedinSub.next(true);
            this.setSignedIn(userDtls);
        } else {
            console.error('no data in response');
            this.setSignedOut();
        }
        return userDtls;
    }
    isUserSignedin(): Observable<boolean> {
        return this.isUserSignedinSub.asObservable();
    }
    getUserSignedin(): Observable<UserDetails> {
        return this.userSignedinSub.asObservable();
    }
    private setSignedIn(userDtls: UserDetails) {
        this.userSignedinSub.next(userDtls);
        this.isUserSignedinSub.next(true);
    }
    private setSignedOut() {
        this.userSignedinSub.next(undefined);
        this.isUserSignedinSub.next(false);
    }

    loginUser(user: User) {
        this.loggedIn = false;

        const userHttpGet: HttpReq = new HttpReq('/api/login');
        userHttpGet.params.append('username', user.userName);
        userHttpGet.params.append('password', user.password);

        return super.httpGet(userHttpGet);
    }

    login(isLoggedIn: boolean): void {
        this.isLoggedinSub.next(isLoggedIn);
    }
    loggedinUser(user: User): void {
        this.userLoggedinSub.next(user);
    }

    getLoggedinUser(): Observable<boolean> {
        return this.isLoggedinSub.asObservable();
    }

    getUserLoggedin(): Observable<User> {
        return this.userLoggedinSub.asObservable();
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
