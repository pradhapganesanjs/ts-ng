
export class HttpReq {
    _params: Object;
    constructor (private _url: string) { 
        this._params = new Object();
    }

    get url(){
        return this._url;
    }
    set url(urlP: string){
        this._url = urlP;
    }
    get params(): any {
        return this._params;
    }
    set params(paramsP: any) {
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
