import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { BookingService } from '../../services/booking';
import { AuthService } from '../../services/auth';
import { Booking } from '../../models/booking.model';

@Component({
  selector: 'app-my-bookings',
  imports: [CommonModule],
  templateUrl: './my-bookings.html',
  styleUrl: './my-bookings.css'
})
export class MyBookingsComponent implements OnInit {
  bookings: Booking[] = [];
  loading = true;
  errorMessage = '';
  userEmail = '';

  constructor(
    private bookingService: BookingService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get email from both sources and use whichever is available
    this.userEmail = this.authService.getEmail() || localStorage.getItem('userEmail') || '';

    if (!this.userEmail) {
      this.router.navigate(['/login']);
      return;
    }

    this.bookingService.getMyBookings(this.userEmail).subscribe({
      next: (data) => {
        this.bookings = data;
        this.loading = false;
      },
      error: (err) => {
        // If API call fails due to CORS or server down, show clear message
        if (err.status === 0) {
          this.errorMessage = 'Cannot connect to the server. Please make sure the Spring Boot backend is running on port 8080.';
        } else {
          this.errorMessage = `Server error (${err.status}). Please try again.`;
        }
        this.loading = false;
      }
    });
  }

  isUpcoming(bookingDate: string): boolean {
    if (!bookingDate) return false;
    return new Date(bookingDate) >= new Date(new Date().toDateString());
  }

  bookMore(): void {
    this.router.navigate(['/movies']);
  }
}
