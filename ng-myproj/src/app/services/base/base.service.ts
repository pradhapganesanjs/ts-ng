import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { HttpReq, HttpRes } from '../helpers/http-util';

@Injectable()
export class BaseService {

    private userParam: URLSearchParams;
    private reqOpts: RequestOptions;
    private headers: Headers;

    constructor(private http: Http) {
        this.userParam = new URLSearchParams();
        this.reqOpts = new RequestOptions();
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.reqOpts.headers = this.headers;
    }

    isAuth(authToken: string) {
        return false;
    }

    httpGet(req: HttpReq) {
        const resp: HttpRes = new HttpRes();
        const url: string = req.url;
        const props: any = Object.getOwnPropertyNames(req.params);

        for (const prop of props){
            this.userParam.append(prop, props[prop]);
        }
        this.reqOpts.params =  this.userParam;

        return this.http.get(url, this.reqOpts)
                 .map(data => {
                        console.log(`http res: ${data}`);
                        resp.response = data.json();
                        if (!data) { throw new Error('something went wrong'); }
                        return resp;
                        });
    }

}
