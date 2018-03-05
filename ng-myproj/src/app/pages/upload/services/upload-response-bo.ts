import { JsonProperty } from 'json-typescript-mapper';

export class UploadResponse {

    @JsonProperty('error_num')
    private _error_num: string;

    @JsonProperty('error_msg')
    private _error_msg: string;

    @JsonProperty('result')
    private _result: any;

    constructor() {
        this._error_num = '';
        this._error_msg = '';
        this._result = '';
    }

    public get error_num(): string {
        return this._error_num;
    }

    public set error_num(value: string) {
        this._error_num = value;
    }


    public get error_msg(): string {
        return this._error_msg;
    }

    public set error_msg(value: string) {
        this._error_msg = value;
    }


    public get result(): any {
        return this._result;
    }

    public set result(value: any) {
        this._result = value;
    }

}
