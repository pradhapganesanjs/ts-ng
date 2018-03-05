import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';

import { deserialize } from 'json-typescript-mapper';

import { User } from './user.bo';
import { UserDetails } from './userDetails.bo';
import { BaseService } from '../base/base.service';
import { HttpReq } from '../helpers/http-util';
import { ApiUrl } from '../api-urls';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { ErrorProcessor } from '../helpers/error-processor';
import { SessionProcessor } from '../helpers/session-processor';

@Injectable()
export class UserService {

    userRes: any;

    private userPost: any = {};

    private headerParamObj;

    constructor(private httpClient: HttpClient, 
                private errProcess: ErrorProcessor,
                private sessPros: SessionProcessor) {
        console.log('UserService Construtor' + this.httpClient);

        const headerParams = new HttpHeaders();
        headerParams.set('Content-Type', 'application/json');
        this.headerParamObj = { headers: headerParams };
    }
    signOut() {
        this.sessPros.doSignOut();
    }

    signinUser(username: string, password: string) {
        console.log(`username : ${username}}`);
        console.log('url ' + ApiUrl.LOGIN);

        return this.httpClient.post(ApiUrl.LOGIN, { username, password }, this.headerParamObj)
            .map(res => this.handleRes(res),
                 err => {
                    return Observable.throw(this.errProcess.getErrorMsg(err)); })
            .catch(excep => {
                return Observable.throw(this.errProcess.getErrorMsg(excep));
            });
    }
    private handleRes(res) {
        let userDtls = null;
        const userRes = res ? res.result : null;
        if (userRes) {
            userDtls = deserialize(UserDetails, userRes);
            console.log(' userDtls ' + userDtls.toString());

            this.sessPros.storeSignIn(userDtls);
            this.sessPros.checkInSignedInRepo();

        } else {
            console.error('no data in response');
            this.sessPros.clearSignIn();
            this.sessPros.checkInSignedInRepo();
            this.errProcess.handleErrResp(res);
        }
        return userDtls;
    }
}
