import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MovieService } from '../../services/movie';
import { Movie } from '../../models/movie.model';

@Component({
  selector: 'app-movies',
  imports: [CommonModule],
  templateUrl: './movies.html',
  styleUrl: './movies.css',
})
export class MoviesComponent implements OnInit {
  movies: Movie[] = [];
  errorMessage = '';
  loading = true;
  emptyMessage = '';

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit(): void {
    this.loading = true;
    this.movieService.getAllMovies().subscribe({
      next: (data) => {
        this.movies = data || [];
        this.loading = false;
        if (this.movies.length === 0) {
          this.emptyMessage = 'No movies available right now. Please check back later.';
        }
      },
      error: (err) => {
        console.error('Failed to load movies:', err);
        this.errorMessage = 'Failed to load movies. Please make sure the server is running.';
        this.loading = false;
      }
    });
  }

  viewMovie(movie: Movie): void {
    this.router.navigate(['/movies', movie.id], { state: { movie } });
  }
}
