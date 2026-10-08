import { Component, inject, signal } from '@angular/core';
import { Film } from '../models/film.model';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError } from 'rxjs/internal/operators/catchError';
import { Observable, of } from 'rxjs';
import { FilmService } from '../services/film.service';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  private service = inject(FilmService);
  erreur = signal<string | null>(null);

  films = toSignal<Film[]>(
    ((this.service.getAll() as Observable<Film[]> | undefined) ?? of([] as Film[])).pipe(
      catchError(() => {
        this.erreur.set('Impossible de charger les films');
        return of([] as Film[]);
      }),
    ),
  );
}

