import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../services/movie';
import { BookingService } from '../../services/booking';
import { Movie } from '../../models/movie.model';
import { Booking } from '../../models/booking.model';

@Component({
  selector: 'app-booking',
  imports: [CommonModule, FormsModule],
  templateUrl: './booking.html',
  styleUrl: './booking.css'
})
export class BookingComponent implements OnInit {
  movie: Movie | null = null;
  errorMessage = '';
  submitting = false;
  todayDate = new Date().toISOString().split('T')[0]; 

  booking: Booking = {
    movieId: 0,
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    numberOfSeats: 1,
    bookingDate: new Date().toISOString().split('T')[0]
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) { this.router.navigate(['/movies']); return; }
    this.booking.movieId = id;

    // Pre-fill email from logged-in user
    this.booking.customerEmail = localStorage.getItem('userEmail') || '';

    // Only use router state movie if its id matches the URL id — prevents stale state bug
    const stateMovie = history.state?.movie;
    if (stateMovie && stateMovie.id === id) {
      this.movie = stateMovie;
      return;
    }

    // Fetch from API (state was stale or missing)
    this.movieService.getMovieById(id).subscribe({
      next: (data) => (this.movie = data),
      error: () => (this.errorMessage = 'Could not load movie details. Please go back and try again.')
    });
  }

  get totalPrice(): number {
    return (this.movie?.price || 0) * (this.booking.numberOfSeats || 0);
  }

  confirmBooking(): void {
    this.submitting = true;
    this.errorMessage = '';
    this.bookingService.createBooking(this.booking).subscribe({
      next: (response) => {
        this.router.navigate(['/booking-confirmation'], { state: { booking: response } });
      },
      error: () => {
        this.errorMessage = 'Booking failed. Please try again.';
        this.submitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/movies', this.movie?.id], { state: { movie: this.movie } });
  }
}
