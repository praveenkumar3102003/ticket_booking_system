import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(private router: Router, private authService: AuthService) {
    if (history.state?.signupSuccess) {
      this.successMessage = 'Account created successfully! Please sign in.';
    }
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/movies']);
    }
  }

  login(): void {
    this.errorMessage = '';
    this.loading = true;
    this.authService.login({ email: this.email.trim(), password: this.password }).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.success) {
          this.authService.setSession(res);
          this.successMessage = 'Logged in successfully! Redirecting...';
          this.router.navigate(['/movies']);
        } else {
          this.errorMessage = res.message;
        }
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Cannot connect to server. Make sure the backend is running.';
      }
    });
  }
}
