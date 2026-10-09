import { Component, computed, inject, signal } from '@angular/core';
import { Film } from '../../models/film.model';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError } from 'rxjs/internal/operators/catchError';
import { Observable, of } from 'rxjs';
import { FilmService } from '../../services/film.service';
import { RouterLink } from '@angular/router';
import { FilmCard } from '../film-card/film-card';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [RouterLink,FilmCard, FormsModule],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  private service = inject(FilmService);
  erreur = signal<string | null>(null);
  private version = signal(0);
  recherche = signal('');



  films = toSignal<Film[]>(
    ((this.service.getAll() as Observable<Film[]> | undefined) ?? of([] as Film[])).pipe(
      catchError(() => {
        this.erreur.set('Impossible de charger les films');
        return of([] as Film[]);
      }),
    ),
  );
  
  //filmsFilter = signal<Film[]>([]);
  
  //chercheFilm(){
  // const valeur = this.recherche().toLowerCase().trim();
  //  const   filmfiltrer =this.films()?.filter((film) => film.titre.toLowerCase().includes(valeur)) ?? [];
  // this.filmsFilter.set(filmfiltrer);
  //}

  filmsFilter = computed(()=>{
    const valeur = this.recherche().toLowerCase().trim();
    return this.films()?.filter((film) => film.titre.toLowerCase().includes(valeur)) ?? [];
  }
  ) ;
 

  onSupprimer(film: Film) {
    if (!confirm(`Voulez-vous vraiment supprimer « ${film.titre} » ?`)) return;
    this.service.supprimer(film.id).subscribe({
      next: () => this.version.update(v => v + 1),
      error: () => this.erreur.set('Impossible de supprimer le film'),
    });
  }
}

