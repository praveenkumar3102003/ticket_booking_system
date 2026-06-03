import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../services/movie';
import { Movie } from '../../models/movie.model';

@Component({
  selector: 'app-movie-detail',
  imports: [CommonModule],
  templateUrl: './movie-detail.html',
  styleUrl: './movie-detail.css'
})
export class MovieDetailComponent implements OnInit {
  movie: Movie | null = null;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) { this.router.navigate(['/movies']); return; }

    
    const stateMovie = history.state?.movie;
    if (stateMovie && stateMovie.id === id) {
      this.movie = stateMovie;
      return;
    }

    this.movieService.getMovieById(id).subscribe({
      next: (data) => (this.movie = data),
      error: () => (this.errorMessage = 'Could not load movie. Please go back and try again.')
    });
  }

  bookMovie(): void {
    this.router.navigate(['/booking', this.movie?.id], { state: { movie: this.movie } });
  }

  goBack(): void {
    this.router.navigate(['/movies']);
  }
}
