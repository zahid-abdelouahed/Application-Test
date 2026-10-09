import { Component, signal, input, inject, computed } from '@angular/core';
import { Film } from '../../models/film.model';
import { Router, RouterLink } from '@angular/router';
import { FilmService } from '../../services/film.service';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { catchError, of, switchMap } from 'rxjs';
import { ActeurService } from '../../services/acteur.service';
import { Acteur } from '../../models/acteur.model';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  imports: [RouterLink, DatePipe, FormsModule],
  selector: 'app-film-detail',
  styleUrl: './film-detail.css',
  templateUrl: './film-detail.html',
})
export class FilmDetail {
  private service = inject(FilmService);
  private serviceacteur = inject(ActeurService);
  private router = inject(Router);
  id = input.required<number>();
  filmId = computed(() => Number(this.id()));
  erreur = signal<string | null>(null);
  acteurSelectionne = signal<number | null>(null);
  private version = signal(0);
  private source = computed(() => ({ id: this.filmId(), version: this.version() }));

  film = toSignal(
    toObservable(this.source).pipe(
      switchMap(({id}) => this.service.getById(id).pipe(
       catchError(() => {
        this.erreur.set('Impossible de charger le film');
        return of(null);
      }),
    ),
    )),
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

  private tousLesActeurs = toSignal(
    this.serviceacteur.getAll().pipe(
      catchError(() => {
        this.erreur.set('Impossible de charger les acteurs');
        return of([] as Acteur[]);
      }),
    ),
    { initialValue: [] as Acteur[] },
  );

  acteursDisponibles = computed(() => {
    const associes = (this.film()?.acteurs ?? []).map(a => a.id);
    return this.tousLesActeurs().filter(a => !associes.includes(a.id));
  });

  private recharger() {
    this.version.update(v => v + 1);
  }

  associer() {
    const acteurId = this.acteurSelectionne();
    if (!acteurId) return;
    this.service.associerActeur(this.filmId(), acteurId).subscribe({
      next: () => {
        this.acteurSelectionne.set(null);
        this.recharger();
      },
      error: () => this.erreur.set('Association impossible'),
    });
  }

  dissocier(acteurId: number) {
    this.service.dissocierActeur(this.filmId(), acteurId).subscribe({
      next: () => this.recharger(),
      error: () => this.erreur.set('Dissociation impossible'),
    });
  }
   
}
