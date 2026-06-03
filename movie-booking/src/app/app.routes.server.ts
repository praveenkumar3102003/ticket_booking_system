import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  { path: 'movies/:id', renderMode: RenderMode.Client },
  { path: 'booking/:id', renderMode: RenderMode.Client },
  { path: 'booking-confirmation', renderMode: RenderMode.Client },
  { path: 'login', renderMode: RenderMode.Client },
  { path: 'signup', renderMode: RenderMode.Client },
  { path: 'movies', renderMode: RenderMode.Client },
  { path: 'my-bookings', renderMode: RenderMode.Client },
  { path: '**', renderMode: RenderMode.Prerender }
];
