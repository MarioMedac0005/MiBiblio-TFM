import { Routes } from '@angular/router';
import { authGuard } from './services/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login').then((m) => m.LoginPageComponent),
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/dashboard/dashboard').then((m) => m.Dashboard),
  },
  {
    path: 'usuarios',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/users/users').then((m) => m.UsersPage),
  },
  {
    path: 'libros',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/books/books').then((m) => m.BooksPage),
  },
  {
    path: 'prestamos',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/loans/loans').then((m) => m.LoansPage),
  },
  {
    path: 'reservas',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/reservations/reservations').then((m) => m.Reservations),
  },
  { path: '**', redirectTo: 'dashboard' }
];
