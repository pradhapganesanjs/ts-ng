import { JsonProperty } from 'json-typescript-mapper';

export class Report {
    @JsonProperty('id')
    private _id: string;

    @JsonProperty('status')
    private _status: string;

    @JsonProperty('sourceStatus')
    private _sourceStatus: string;

    @JsonProperty('sourceVersion')
    private _sourceVersion: string;

    @JsonProperty('regReportingRef')
    private _regReportingRef: string;

    @JsonProperty('rdsEligible')
    private _rdsEligible: boolean;

    @JsonProperty('receivedTs')
    private _receivedTs: number;

    @JsonProperty('stream')
    private _stream: string;

    @JsonProperty('flow')
    private _flow: string;

    @JsonProperty('sourceSystem')
    private _sourceSystem: string;

    @JsonProperty('sourceId')
    private _sourceId: string;

    @JsonProperty('sourceUId')
    private _sourceUId: string;

    constructor() {
        this._id = undefined;
        this._flow = undefined;
        this._sourceId = undefined;
        this._sourceUId = undefined;
        this._sourceSystem = undefined;
        this._sourceVersion = undefined;
        this._rdsEligible = undefined;
        this._stream = undefined;
        this._receivedTs = undefined;
        this._regReportingRef = undefined;
    }

    public get id(): string {
        return this._id;
    }

    public set id(value: string) {
        this._id = value;
    }

    public get status(): string {
        return this._status;
    }

    public set status(value: string) {
        this._status = value;
    }

    public get stream(): string {
        return this._stream;
    }

    public set stream(value: string) {
        this._stream = value;
    }

    public get flow(): string {
        return this._flow;
    }

    public set flow(value: string) {
        this._flow = value;
    }

    public get sourceSystem(): string {
        return this._sourceSystem;
    }

    public set sourceSystem(value: string) {
        this._sourceSystem = value;
    }


    public get sourceId(): string {
        return this._sourceId;
    }

    public set sourceId(value: string) {
        this._sourceId = value;
    }

    public get sourceUId(): string {
        return this._sourceUId;
    }

    public set sourceUId(value: string) {
        this._sourceUId = value;
    }

    public get sourceStatus(): string {
        return this._sourceStatus;
    }

    public set sourceStatus(value: string) {
        this._sourceStatus = value;
    }

    public get sourceVersion(): string {
        return this._sourceVersion;
    }

    public set sourceVersion(value: string) {
        this._sourceVersion = value;
    }

    public get regReportingRef(): string {
        return this._regReportingRef;
    }

    public set regReportingRef(value: string) {
        this._regReportingRef = value;
    }

    public get rdsEligible(): boolean {
        return this._rdsEligible;
    }

    public set rdsEligible(value: boolean) {
        this._rdsEligible = value;
    }

    public get receivedTs(): number {
        return this._receivedTs;
    }

    public set receivedTs(value: number) {
        this._receivedTs = value;
    }

}
