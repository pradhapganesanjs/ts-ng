export interface IAlertMsg {
    type: AlertType;
    msg: string;
    code?: string;
}
export enum AlertType {
    ERROR = 'alert-danger',
    WARN = 'alert-warning',
    SUCCESS = 'alert-success'
}
