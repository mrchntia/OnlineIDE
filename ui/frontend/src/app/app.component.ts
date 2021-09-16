import { Component } from '@angular/core';
import {AuthService} from "./auth.service";
import {HttpService} from "./service/http.service";
import {Router} from "@angular/router";
import {async} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  constructor(public authService: AuthService) {
  }
}
