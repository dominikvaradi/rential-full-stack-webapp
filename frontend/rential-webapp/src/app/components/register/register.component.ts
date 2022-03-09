import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {
    username: null,
    password: null,
    email: null,
    firstname: null,
    lastname: null
  };

  isSuccessful: boolean = false;

  isSignUpFailed: boolean = false;
  
  errorMessage: string = '';

  constructor(private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(this.tokenStorageService.getToken()) {
      this.router.navigate(['/home']);
    }
  }

  onSubmit(): void {
    const { username, password, email, firstname, lastname } = this.form;

    this.authService.register(username, password, email, firstname, lastname).subscribe({
      next: v => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: e => {
        this.isSignUpFailed = true;
        this.errorMessage = e.error.message;
      }
    });
  }
}

