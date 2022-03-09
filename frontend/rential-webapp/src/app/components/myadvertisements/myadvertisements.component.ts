import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from '../../services/advertisement.service';
import { Advertisement } from '../../models/Advertisement';
import { TokenStorageService } from '../../services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-myadvertisements',
  templateUrl: './myadvertisements.component.html',
  styleUrls: ['./myadvertisements.component.css']
})
export class MyAdvertisementsComponent implements OnInit {
  currentUserId?: number;

  advertisements: Advertisement[] = [];

  constructor(private tokenStorageService: TokenStorageService, private advertisementService: AdvertisementService, private router: Router) { }

  ngOnInit(): void {
    this.currentUserId = this.tokenStorageService.getId();

    if(!this.currentUserId) {
      this.router.navigate(['/home']);
    }

    this.advertisementService.getAdvertisementsByUserId(this.currentUserId).subscribe({
      next: v => {
        this.advertisements = v;
      },
      error: e => {
        this.advertisements = [];
      }
    });
  }

  deleteAdvertisement(id: number) {
    this.advertisementService.deleteAdvertisement(id).subscribe({
      next: v => {
        window.location.reload();
      },
      error: e => {
        alert(`There was an error occured! ${e.error.message}`);
      }
    });
  }
}
