import { BaseServiceReqBo } from '../base/base.service.req';

export class User extends BaseServiceReqBo {
    private _userName: string;
    private _password: string;

    get userName(){
        return this._userName;
    }
    set userName(name: string){
        this._userName = name;
    }
    get password(){
        return this._password;
    }
    set password(pass: string){
        this._password = pass;
    }
}
