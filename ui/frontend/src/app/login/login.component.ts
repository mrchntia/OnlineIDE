import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public authService: AuthService, public router: Router) {
  }

  ngOnInit(): void {
  }

  public navigateToProjectList(): void {
    this.router.navigateByUrl('/ui/projects');
  }
}
