
export interface ReportCriteria {
    id?: string;
    status?: string;
    flow?: string;
    sourceId?: string;
    sourceUId?: string;
    sourceSystem?: string;
    sourceVersion?: string;
    rdsEligible?: boolean;
    stream?: string;
    receivedTs?: string;
    regReportingRef?: string;
}

/*
export class ReportCriteria {
    private _id: string;
    private _status: string;
    private _flow: string;
    private _sourceId: string;
    private _sourceUId: string;
    private _sourceSystem: string;
    private _sourceVersion: string;
    private _rdsEligible: boolean;
    private _stream: string;
    private _receivedTs: string;
    private _regReportingRef: string;

    constructor() {

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

    public get flow(): string {
        return this._flow;
    }

    public set flow(value: string) {
        this._flow = value;
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

    public get sourceSystem(): string {
        return this._sourceSystem;
    }

    public set sourceSystem(value: string) {
        this._sourceSystem = value;
    }

    public get sourceVersion(): string {
        return this._sourceVersion;
    }

    public set sourceVersion(value: string) {
        this._sourceVersion = value;
    }

    public get rdsEligible(): boolean {
        return this._rdsEligible;
    }

    public set rdsEligible(value: boolean) {
        this._rdsEligible = value;
    }

    public get stream(): string {
        return this._stream;
    }

    public set stream(value: string) {
        this._stream = value;
    }

    public get receivedTs(): string {
        return this._receivedTs;
    }

    public set receivedTs(value: string) {
        this._receivedTs = value;
    }

    public get regReportingRef(): string {
        return this._regReportingRef;
    }

    public set regReportingRef(value: string) {
        this._regReportingRef = value;
    }
}
*/

export const ReportCriteriaObj: ReportCriteria = {
    id: ''
    , status: ''
    , flow: ''
    , sourceId: ''
    , sourceUId: ''
    , sourceSystem: ''
    , sourceVersion: ''
    , rdsEligible: false
    , stream: ''
    , receivedTs: ''
    , regReportingRef: ''
};

