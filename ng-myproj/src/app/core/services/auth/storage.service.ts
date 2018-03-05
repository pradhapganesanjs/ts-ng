import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

  constructor() { }

  public getAuthToken(): string {
    return window.localStorage.getItem('auth_token');
  }

}
