import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USERNAME_KEY = 'auth-user-username';
const ROLES_KEY = 'auth-user-roles';
const ID_KEY = 'auth-user-roles';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string): void {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  public saveRoles(roles: string[]): void {
    window.sessionStorage.removeItem(ROLES_KEY);
    window.sessionStorage.setItem(ROLES_KEY, JSON.stringify(roles));
  }

  public saveId(id: number): void {
    window.sessionStorage.removeItem(ID_KEY);
    window.sessionStorage.setItem(ID_KEY, id.toString());
  }

  public getUsername(): string | null {
    return window.sessionStorage.getItem(USERNAME_KEY);
  }

  public getId(): number {
    return Number.parseInt(window.sessionStorage.getItem(ID_KEY) ?? "0");
  }

  public getRoles(): string[] {
    const roles = window.sessionStorage.getItem(ROLES_KEY);

    if (roles) {
      return JSON.parse(roles);
    }
    return [];
  }
}
