import { Component, signal, input, inject, computed } from '@angular/core';
import { Film } from '../models/film.model';
import { Router, RouterLink } from '@angular/router';
import { FilmService } from '../services/film.service';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { catchError, of, switchMap } from 'rxjs';
@Component({
  imports: [RouterLink],
  selector: 'app-film-detail',
  styleUrl: './film-detail.css',
  templateUrl: './film-detail.html',
})
export class FilmDetail {
  private service = inject(FilmService);
  private router = inject(Router);
  id = input.required<number>();
  filmId = computed(() => Number(this.id()));
  erreur = signal<string | null>(null);

  film = toSignal(
    toObservable(this.filmId).pipe(
      switchMap(id => this.service.getById(id)),
      catchError(() => {
        this.erreur.set('Impossible de charger le film');
        return of(null);
      }),
    )
  );

  supprimer() {
    if (!confirm('Voulez-vous vraiment supprimer ce film ?')) return;
      this.service.supprimer(this.filmId()).subscribe({
        next: () => {
          this.router.navigate(['/films']);
        },
        error: () => {
          this.erreur.set('Impossible de supprimer le film')
        }
      });
  }
   
}
