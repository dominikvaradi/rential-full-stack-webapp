import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-createadvertisement',
  templateUrl: './createadvertisement.component.html',
  styleUrls: ['./createadvertisement.component.css']
})
export class CreateAdvertisementComponent implements OnInit {
  form: any = {
    name: null,
    description: null
  };

  isSuccessful: boolean = false;

  isSignUpFailed: boolean = false;
  
  errorMessage: string = '';

  constructor(private advertisementService: AdvertisementService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(!this.tokenStorageService.getToken()) {
      this.router.navigate(['/home']);
    }
  }

  onSubmit(): void {
    this.advertisementService.createAdvertisement(this.form.name, this.form.description).subscribe({
      next: v => {
        this.isSignUpFailed = false;
        this.isSuccessful = true;
      },
      error: e => {
        this.isSignUpFailed = true;
        this.isSuccessful = false;
        this.errorMessage = e.error.message;
      }
    });
  }
}
