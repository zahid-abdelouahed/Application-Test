import { Component, inject, signal } from '@angular/core';
import { ActeurService } from '../services/acteur.service';
import { catchError, of } from 'rxjs';
import { toSignal } from '@angular/core/rxjs-interop';
import { Acteur } from '../models/acteur.model';
import { RouterLink } from '@angular/router';

@Component({
  imports: [RouterLink],
  selector: 'app-acteur-list',
  styleUrl: './acteur-list.css',
  templateUrl: './acteur-list.html',
})
export class ActeurList {
  private service = inject(ActeurService);
  erreur = signal<string | null>(null);

  acteurs = toSignal(
    this.service.getAll().pipe(
      catchError(() => {
        this.erreur.set('Impossible de charger les acteurs');
        return of([] as Acteur[]);
      }),
    ),
  );
}
