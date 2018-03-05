import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { HttpReq, HttpRes } from '../helpers/http-util';

@Injectable()
export class BaseService {

    constructor(private http: Http) {
        console.log('BaseService Construtor'
        + this.http);
    }

    isAuth(authToken: string) {
        return false;
    }

    httpGet(req: HttpReq) {
        const resp: HttpRes = new HttpRes();
        const url: string = req.url;
        const urlParams: URLSearchParams = req.params;
        /*
        const props: any = Object.getOwnPropertyNames(req.params);
        console.log('props ' + props);
        const httpParam = new URLSearchParams();
        for (const prop of props){
            const p: string = prop;
            httpParam.append(p, props[prop]);
        }
        console.log(' httpParam ' + httpParam);
        */
        const httpHeaders = new Headers();
        httpHeaders.append('Content-Type', 'application/json');

        const reqOpts = new RequestOptions({headers: httpHeaders, params: req.params});
        console.log(' reqOpts.params ' + reqOpts.params);
        return this.http.get(url, reqOpts)
                 .map(data => {
                        console.log(`http res: ${data}`);
                        resp.response = data.json();
                        if (!data) { throw new Error('something went wrong'); }
                        return resp;
                        });
    }

}
