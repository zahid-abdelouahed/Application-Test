import { Component, signal } from '@angular/core';
import { Film } from '../models/film.model';

@Component({
  imports: [],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  films = signal<Film[]>([]);
  chargement = signal(true);
  erreur = signal<string | null>(null);

  ngOnInit() {
    this.charger()
  }

  charger() {
    this.erreur.set(null);
  }
}
