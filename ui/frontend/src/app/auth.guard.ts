import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service'
import {tap} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> {
    return this.authService.authenticated
    // pipe each value that is emitted by< the observalbe
    .pipe(
    //tap will intercept each emission of a value and perform some action
    tap(authenticated => {
    if (!authenticated) {
      // if user is not authenticated, try login via 'AuthService'
      this.authService.login();
      }
      }));
  }

}
