import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { SessionProcessor } from './session-processor';
import { Router } from '@angular/router';


@Injectable()
export class ErrorProcessor {

    constructor(private sessPros: SessionProcessor, private router: Router) {
    }


    getErrorMsg(err) {
        let errMsg = '';
        if (err instanceof Error) {
            console.error('ErrorProcessor Error:', err.message);
            errMsg = `${err.name}${err.name ? ' : ' : ''}${err.message}`;
        } else if (err instanceof HttpErrorResponse) {
            const httpErrResp = <HttpErrorResponse>err;
            const headerErrMsg = httpErrResp.message;
            console.error('ErrorProcessor HttpErrorResponse:', httpErrResp);
            console.error('httpErrResp:', headerErrMsg);
            errMsg = headerErrMsg;
            if (err.status === 401 || err.status === 403) {
                console.log('401 handling it in ErrorProcessor');
                this.sessPros.doSignOut();
                this.router.navigate(['/home']);
              }
        } else {
            console.error(`Backend returned code ${err.status}, body was: ${err.error}`);
            errMsg = `${err.status} : ${err.error}`;
        }
        return errMsg;
    }

    handleErrResp(errResp) {
        const err = new Error('Failed');

        if (errResp && errResp.error_msg) {
            err.message = errResp.error_msg;
        }
        if (errResp && errResp.error_num) {
            err.name = errResp.error_num;
        }

        throw err;
    }

}
