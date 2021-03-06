import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/auth/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: String, password: String): Observable<any> {
    return this.http.post(AUTH_API + "login", {
      username,
      password
    }, httpOptions);
  }

  register(username: String, password: String, email: String, firstname: String, lastname: String): Observable<any> {
    return this.http.post(AUTH_API + "register", {
      username,
      password,
      email,
      firstname,
      lastname
    }, httpOptions);
  }
}