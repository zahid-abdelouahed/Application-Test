import { Component, inject, input, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FilmSaisie, FilmService } from '../../services/film.service';
import { Genre, genres } from '../../models/film.model';
import { toObservable } from '@angular/core/rxjs-interop';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [RouterLink, FormsModule],
  standalone: true,
  selector: 'app-film-form',
  styleUrl: './film-form.css',
  templateUrl: './film-form.html',
})
export class FilmForm {
  private service = inject(FilmService);
  private router = inject(Router);

  id = input<string>();
  genres = genres;
  titre = signal('');
  realisateur = signal('');
  datedesortie = signal<string | null>(null);
  genre = signal<Genre>('ACTION');
  erreur = signal<string | null>(null);

  constructor() {
    toObservable(this.id).subscribe(id => {
      if (id) {
        this.service.getById(Number(id)).subscribe({
          next: film => {
            this.titre.set(film.titre);
            this.realisateur.set(film.realisateur);
            this.datedesortie.set(film.dateSortie);
            this.genre.set(film.genre);
          },
          error: () => {
            this.erreur.set('Impossible de charger le film');
          }
        });
      }
    });
  }

  enregistrer() {
    const film: FilmSaisie = {
      titre: this.titre(),
      realisateur: this.realisateur(),
      dateSortie: this.datedesortie() || null,
      genre: this.genre(),
    };
    const id = this.id();
    const requete = id ? this.service.modifier(Number(id), film) : this.service.creer(film);
    requete.subscribe({
      next: (f) => this.router.navigate(['/films', f.id]),
      error: () => this.erreur.set('Enregistrement impossible. Vérifiez les champs.'),
    });
  } 
  
}
