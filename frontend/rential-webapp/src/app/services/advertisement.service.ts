import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Advertisement } from '../models/Advertisement';

const API_URL = 'http://localhost:8080/api';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  constructor(private http: HttpClient) { }

  getAdvertisements(): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>(API_URL + "/advertisement");
  }

  getAdvertisementById(id: number): Observable<Advertisement> {
    return this.http.get<Advertisement>(API_URL + `/advertisement/${id}`);
  }

  getAdvertisementsByUserId(id: number): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>(API_URL + `/user/${id}/advertisements`);
  }

  deleteAdvertisement(id: number): Observable<any> {
    return this.http.delete<Advertisement>(API_URL + `/advertisement/${id}`);
  }

  createAdvertisement(name: string, description: string): Observable<any> {
    return this.http.post(API_URL + "/advertisement", {
      name,
      description
    }, httpOptions);
  }

  editAdvertisement(advertisement: Advertisement): Observable<any> {
    return this.http.put(API_URL + `/advertisement/${advertisement.id}`, {
      "id": advertisement.id,
      "name": advertisement.name,
      "description": advertisement.description
    }, httpOptions);
  }
}
