import { Routes } from '@angular/router';
import { FilmList } from './Components/film-list/film-list';
import { FilmDetail } from './Components/film-detail/film-detail';
import { FilmForm } from './Components/film-form/film-form';
import { ActeurList } from './Components/acteur-list/acteur-list';
import { ActeurDetail } from './Components/acteur-detail/acteur-detail';
import { NotFound } from './Components/not-found/not-found';

export const routes: Routes = [
  { path: '', redirectTo: 'films', pathMatch: 'full' },
  { path: 'films', component: FilmList },
  { path: 'films/nouveau', component: FilmForm },
  { path: 'films/:id/modifier', component: FilmForm },
  { path: 'films/:id', component: FilmDetail },
  { path: 'acteurs', component: ActeurList },
  { path: 'acteurs/:id', component: ActeurDetail },
  { path: '**', component: NotFound },
];
