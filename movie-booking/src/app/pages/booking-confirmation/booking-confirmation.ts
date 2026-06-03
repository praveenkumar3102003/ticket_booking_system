import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Booking } from '../../models/booking.model';

@Component({
  selector: 'app-booking-confirmation',
  imports: [CommonModule],
  templateUrl: './booking-confirmation.html',
  styleUrl: './booking-confirmation.css'
})
export class BookingConfirmationComponent implements OnInit {
  booking: Booking | null = null;

  constructor(private router: Router) {}

  ngOnInit(): void {
    
    const state = history.state;
    if (state && state.booking && state.booking.id) {
      this.booking = state.booking;
    } else {
     
      this.router.navigate(['/movies']);
    }
  }

  goHome(): void {
    this.router.navigate(['/movies']);
  }
}
