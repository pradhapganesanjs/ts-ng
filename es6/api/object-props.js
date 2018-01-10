class ReqObj{ }

class HttpReq {
    constructor(){
        this._url;
        this._params;
    }
    get url(){
        return this._url;
    }
    set url(urlP){
        this._url = urlP;
    }
    get params(){
        return this._params;
    }
    set params(paramsP) {
        this._params = paramsP;
    }
}

    let myObj = new ReqObj();
    myObj.username="pradhap";
    myObj.password="pass";
    Object.getOwnPropertyNames(myObj).forEach(k=> console.log(`prop: ${k} , value: ${myObj[k]}`));

    let hr = new HttpReq();
    hr.username="ganesan";
    hr.password="pg";
    Object.getOwnPropertyNames(hr).forEach(k=> console.log(`hr prop: ${k} , value: ${hr[k]}`));

    props = Object.getOwnPropertyNames(myObj);
    for(let p of props){
        console.log(p)
    }
