import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpSentEvent,
  HttpHeaderResponse,
  HttpProgressEvent,
  HttpResponse,
  HttpUserEvent,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { StorageService } from './storage.service';


@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private store: StorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler)
    : Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {
    console.log(' interceptting...');
    const token = this.store.getAuthToken();
    console.log(' token ' + token);
    if (token) {
      const reqCloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });

      console.log('return cloned req');
      return next.handle(reqCloned)
      /*
        .do((event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            console.log('httpinterceptor success resp');
          }
        },
        (err: any) => {
          console.log('httpinterceptor err ' + err);
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401 || err.status === 403) {
              console.log('httpinterceptor HttpErrorResponse 401');
              return Observable.throw(err);
            }
          }
        })*/
        .catch( (err: any) => {
          console.log('httpinterceptor err ' + err);

          if (err instanceof Error) {
            const errMsg = `${err.name}${err.name ? ' : ' : ''}${err.message}`;
            console.error('ErrorProcessor Error:', errMsg);
        } else if (err instanceof HttpErrorResponse) {
            const httpErrResp = <HttpErrorResponse>err;
            const headerErrMsg = httpErrResp.message;
            console.error('ErrorProcessor HttpErrorResponse:', httpErrResp);
            console.error('httpErrResp:', headerErrMsg);
        } else {
            console.error(`Backend returned code ${err.status}, body was: ${err.error}`);
        }
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401 || err.status === 403) {
              console.log('httpinterceptor HttpErrorResponse 401');
            }
          }
          return Observable.throw(err);
        });
      /*
        .catch( (err: any) => {
          if (err instanceof HttpErrorResponse) {
          }
          */
    } else {
      console.log('return orig req');
      return next.handle(req);
    }
  }


}
