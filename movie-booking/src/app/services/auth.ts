import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const LOGGED_IN_KEY = 'loggedIn';
const USER_EMAIL_KEY = 'userEmail';
const USER_NAME_KEY = 'userName';

export interface AuthRequest {
  name?: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  success: boolean;
  message: string;
  name: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  isLoggedIn = signal(localStorage.getItem(LOGGED_IN_KEY) === 'true');
  userName = signal(localStorage.getItem(USER_NAME_KEY) || localStorage.getItem(USER_EMAIL_KEY) || '');

  constructor(private http: HttpClient) {}

  signup(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/signup`, request);
  }

  login(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request);
  }

  setSession(response: AuthResponse): void {
    localStorage.setItem(LOGGED_IN_KEY, 'true');
    localStorage.setItem(USER_EMAIL_KEY, response.email);
    localStorage.setItem(USER_NAME_KEY, response.name || response.email);
    this.isLoggedIn.set(true);
    this.userName.set(response.name || response.email);
  }

  logout(): void {
    localStorage.removeItem(LOGGED_IN_KEY);
    localStorage.removeItem(USER_EMAIL_KEY);
    localStorage.removeItem(USER_NAME_KEY);
    this.isLoggedIn.set(false);
    this.userName.set('');
  }

  getEmail(): string {
    return localStorage.getItem(USER_EMAIL_KEY) || '';
  }
}
