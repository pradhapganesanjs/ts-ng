
export class BaseServiceReqBo {

    private _authToken: string;

    get authToken(){
        return this._authToken;
    }
    set authToken(authTokenP: string) {
        this._authToken = authTokenP;
    }
}
