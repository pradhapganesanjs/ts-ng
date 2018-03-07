import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';

import { HttpClient } from '@angular/common/http';
import { UploadResponse } from './upload-response-bo';

import { deserialize } from 'json-typescript-mapper';

import { ApiUrl, UserService, ErrorProcessor, SessionProcessor } from '@app/core';

@Injectable()
export class UploadService {

  private userid = '';

  constructor(private http: HttpClient, private errPros: ErrorProcessor, private sessPros: SessionProcessor) {
    this.sessPros.getUserSignedin().subscribe(usr => {
      usr ? this.userid = usr.userName : this.userid = null;
      console.log('upsrv id : ' + this.userid);
    });
  }

  uploadConfig(file: File, fileUploadType: string): Observable<UploadResponse> {

    const formData: FormData = this.createFormData(file, fileUploadType);

    console.log('uploadConfig URL ' + ApiUrl.UPLOAD_CONFIG_DATA);
    return this.http.post(ApiUrl.UPLOAD_CONFIG_DATA, formData)
      .map(resp => {
        return this.mapResToUploadRes(resp);
      },
      err => {
        return Observable.of(this.handleUploadErr(err));
      })
      .catch(err => {
        return Observable.of(this.handleUploadErr(err));
      });
  }


  upload(file: File, fileUploadType: string): Observable<UploadResponse> {

    const formData: FormData = this.createFormData(file, fileUploadType);
    console.log('upload URL ' + ApiUrl.UPLOAD);

    return this.http.post(ApiUrl.UPLOAD, formData)
      .map(resp => {
        return this.mapResToUploadRes(resp);
      },
      err => {
        return Observable.of(this.handleUploadErr(err));
      })
      .catch(err => {
        return Observable.of(this.handleUploadErr(err));
      });
  }

  private createFormData(file: File, fileUploadType: string): FormData {
    const formData: FormData = new FormData();

    formData.append('file', file, file.name);
    formData.append('filetype', fileUploadType);
    formData.append('userid', this.userid);

    console.log(`UploadService ${file.name}, ${file.type}`);
    return formData;
  }
  private mapResToUploadRes(resp: any) {
    console.log('post resp ' + resp);
    return deserialize(UploadResponse, resp);
  }

  private handleUploadErr(err): UploadResponse {
    console.error('file upload err ' + err);
    const upRes = new UploadResponse();
    upRes.error_num = '-100';
    upRes.error_msg = this.errPros.getErrorMsg(err);
    upRes.result = null;
    return upRes;
  }
}
