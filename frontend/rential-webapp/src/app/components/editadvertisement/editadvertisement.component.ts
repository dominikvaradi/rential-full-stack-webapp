import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Advertisement } from 'src/app/models/Advertisement';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-editadvertisement',
  templateUrl: './editadvertisement.component.html',
  styleUrls: ['./editadvertisement.component.css']
})
export class EditAdvertisementComponent implements OnInit {
  advertisement: Advertisement = new Advertisement(0, "", "", "");

  errorMessage: string = '';

  isAdvertisementEditable: boolean = false;

  isEditFailed: boolean = false;

  isSuccessful: boolean = false;

  constructor(private route: ActivatedRoute, private advertisementService: AdvertisementService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if(!this.tokenStorageService.getToken()) {
      this.router.navigate(['/home']);
    }

    this.route.params.subscribe(params => {
      this.advertisementService.getAdvertisementById(params["id"]).subscribe({
        next: v => {
          if (v.username != this.tokenStorageService.getUsername() && (Array.isArray(this.tokenStorageService.getRoles()) ? !this.tokenStorageService.getRoles().includes("ROLE_ADMIN") : true)) {
            this.router.navigate(['/home']);
          }

          this.advertisement = v;
          this.isAdvertisementEditable = true;
        },
        error: e => {
          this.errorMessage = e.error.message;
          this.isAdvertisementEditable = false;

          this.router.navigate(['/home']);
        }
      });
    })
  }

  onSubmit(): void {
    this.advertisementService.editAdvertisement(this.advertisement).subscribe({
      next: v => {
        this.isSuccessful = true;
        this.isEditFailed = false;
      },
      error: e => {
        this.isSuccessful = false;
        this.isEditFailed = true;
        this.errorMessage = e.error.message;
      }
    });
  }
}
