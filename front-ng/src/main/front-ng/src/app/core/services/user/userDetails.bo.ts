import { JsonProperty } from 'json-typescript-mapper';

export class UserDetails {

    @JsonProperty('id')
    private _id: string;

    @JsonProperty('userId')
    private _userName: string;

    private _userRole: string;
    private _userGroup: string;

    @JsonProperty('active')
    private _userStatus: string;

    @JsonProperty('authToken')
    private _authToken: string;

    constructor() {
        this._id = undefined;
        this._userName = undefined;
        this._userRole = undefined;
        this._userGroup = undefined;
        this._userStatus = undefined;
        this._authToken =  undefined;
    }


    public get id(): string {
        return this._id;
    }

    public set id(value: string) {
        this._id = value;
    }


    public get userName(): string {
        return this._userName;
    }

    public set userName(value: string) {
        this._userName = value;
    }



    public get userRole(): string {
        return this._userRole;
    }

    public set userRole(value: string) {
        this._userRole = value;
    }


    public get userGroup(): string {
        return this._userGroup;
    }

    public set userGroup(value: string) {
        this._userGroup = value;
    }


    public get userStatus(): string {
        return this._userStatus;
    }

    public set userStatus(value: string) {
        this._userStatus = value;
    }

    public get authToken(): string {
        return this._authToken;
    }

    public set authToken(value: string) {
        this._authToken = value;
    }

    public toString(): string {
        return `id: ${this._id}, userName: ${this._userName}, userRole: ${this._userRole}, userStatus: ${this._userStatus}`;
    }
}
