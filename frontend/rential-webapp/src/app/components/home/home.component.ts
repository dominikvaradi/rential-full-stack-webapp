import { Component, OnInit } from '@angular/core';
import { AnyCatcher } from 'rxjs/internal/AnyCatcher';
import { AdvertisementService } from '../../services/advertisement.service';
import { Advertisement } from '../../models/Advertisement';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  advertisements: Advertisement[] = [];

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getAdvertisements().subscribe({
      next: v => {
        this.advertisements = v;
      },
      error: e => {
        this.advertisements = [];
      }
    });
  }
}
