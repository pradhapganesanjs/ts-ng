import { URLSearchParams } from '@angular/http';

export class HttpReq {
    _params: URLSearchParams;
    constructor (private _url: string) {
        this._params = new URLSearchParams();
    }

    get url(){
        return this._url;
    }
    set url(urlP: string){
        this._url = urlP;
    }
    get params(): URLSearchParams {
        return this._params;
    }
    set params(paramsP: URLSearchParams) {
        this._params = paramsP;
    }
}

export class HttpRes {
    private _response: any;
    constructor () { }

    get response(){
        return this._response;
    }
    set response(resP: Map<string, string>){
        this._response = resP;
    }
}
