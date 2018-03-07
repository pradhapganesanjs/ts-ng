import { Injectable } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { IAlertMsg, AlertType } from './i-alert-msg';

@Injectable()
export class AlertService {

  alertSub: BehaviorSubject<Map<string, string>> = new BehaviorSubject(undefined);
  alertObs: Observable<Map<string, string>> = this.alertSub.asObservable();

  alertMsgSub: BehaviorSubject<IAlertMsg> = new BehaviorSubject(undefined);
  alertMsgObs: Observable<IAlertMsg> = this.alertMsgSub.asObservable();

  showAlertSuccessMsg(errMsg) {
    const alertMsg: IAlertMsg = this.prepareSuccess(errMsg);
    console.log('msg emit from alertSrv');
    this.emitAlertMsg(alertMsg);
  }

  showAlertErrMsg(errMsg) {
    const alertMsg: IAlertMsg = this.prepareErr(errMsg);
    console.log('msg emit from alertSrv');
    this.emitAlertMsg(alertMsg);
  }

  emitAlert(emits: Map<string, string>) {
    this.alertSub.next(emits);
  }

  emitAlertMsg(emits: IAlertMsg) {
    this.alertMsgSub.next(emits);
  }

  private prepareErr(err) {
    console.log(err);
    const alertMsg = {} as IAlertMsg;
    alertMsg.msg = err;
    alertMsg.type = AlertType.ERROR;
    return alertMsg;
  }

  private prepareSuccess(msg): IAlertMsg {
    console.log(msg);
    const alertMsg: IAlertMsg = {} as IAlertMsg;
    alertMsg.msg = msg;
    alertMsg.type = AlertType.SUCCESS;
    return alertMsg;
  }
}
