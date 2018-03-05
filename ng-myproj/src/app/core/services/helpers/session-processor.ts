import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { UserDetails } from '../user/userDetails.bo';

@Injectable()
export class SessionProcessor {

  private userSignedinSub = new BehaviorSubject<UserDetails>(undefined);
  private isUserSignedinSub = new BehaviorSubject<boolean>(false);

  constructor() { }

  isUserSignedin(): Observable<boolean> {
    this.checkInSignedInRepo();
    return this.isUserSignedinSub.asObservable();
  }
  getUserSignedin(): Observable<UserDetails> {
    this.checkInSignedInRepo();
    return this.userSignedinSub.asObservable();
  }

  setUserSignedin(): Observable<UserDetails> {
    this.checkInSignedInRepo();
    return this.userSignedinSub.asObservable();
  }

  checkInSignedInRepo() {
    const singedInName = window.localStorage.getItem('signed-user');
    if (singedInName) {
      const usr = new UserDetails();
      usr.userName = singedInName;
      this.userSignedinSub.next(usr);
      this.isUserSignedinSub.next(true);
    } else {
      this.userSignedinSub.next(undefined);
      this.isUserSignedinSub.next(false);
    }
  }

  storeSignIn(userDtls) {
    window.localStorage.setItem('signed-user', `${userDtls.userName}`);
    window.localStorage.setItem('auth_token', `${userDtls.authToken}`);
  }

  clearSignIn() {
    window.localStorage.removeItem('signed-user');
    window.localStorage.removeItem('auth_token');
  }

  doSignOut() {
    this.clearSignIn();
    this.checkInSignedInRepo();
  }

}
