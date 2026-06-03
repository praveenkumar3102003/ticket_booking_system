import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { SignupComponent } from './pages/signup/signup';
import { MoviesComponent } from './pages/movies/movies';
import { MovieDetailComponent } from './pages/movie-detail/movie-detail';
import { BookingComponent } from './pages/booking/booking';
import { BookingConfirmationComponent } from './pages/booking-confirmation/booking-confirmation';
import { MyBookingsComponent } from './pages/my-bookings/my-bookings';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'movies', component: MoviesComponent, canActivate: [authGuard] },
  { path: 'movies/:id', component: MovieDetailComponent, canActivate: [authGuard] },
  { path: 'booking/:id', component: BookingComponent, canActivate: [authGuard] },
  { path: 'booking-confirmation', component: BookingConfirmationComponent, canActivate: [authGuard] },
  { path: 'my-bookings', component: MyBookingsComponent, canActivate: [authGuard] }
];
