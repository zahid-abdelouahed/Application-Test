import { Component, computed, inject, input, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ActeurService } from '../../services/acteur.service';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { catchError, of, switchMap } from 'rxjs';
import { Film } from '../../models/film.model';
import { DatePipe } from '@angular/common';

@Component({
  imports: [RouterLink,DatePipe],
  selector: 'app-acteur-detail',
  styleUrl: './acteur-detail.css',
  templateUrl: './acteur-detail.html',
})
export class ActeurDetail {
  private service = inject(ActeurService);
  id = input.required<number>();
  acteurId = computed(()=> Number(this.id()));
  erreur = signal<string | null>(null);

  acteur = toSignal(
    toObservable(this.acteurId).pipe(
      switchMap(id=> this.service.getById(id).pipe(
        catchError(()=>{
          this.erreur.set("Impossible de charger l'acteur");
          return of(null);
        }),
      )),
    ),
  );

  films = toSignal(
    toObservable(this.acteurId).pipe(
      switchMap(id=> this.service.getFilms(id).pipe(
        catchError(()=>{
          this.erreur.set("Impossible de charger les films de cet acteur");
          return of([] as Film[]);
        }),
      )),
    ),
  );

}
