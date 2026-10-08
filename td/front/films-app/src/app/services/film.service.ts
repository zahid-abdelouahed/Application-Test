import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Film, Genre } from "../models/film.model";
import { Acteur } from "../models/acteur.model";

export interface FilmSaisie {
  titre: string;
  realisateur: string;
  dateSortie: string | null;
  genre: Genre;
}
@Injectable({ providedIn: 'root' })
export class FilmService {
  private http = inject(HttpClient);
  private url = '/api/films';

  getAll() {
    return this.http.get<Film[]>(this.url);
  }

  getById(id: number) {
    return this.http.get<Film>(`${this.url}/${id}`);
  }

  getActeurs(id: number) {
    return this.http.get<Acteur[]>(`${this.url}/${id}/acteurs`);
  }

  creer(film: FilmSaisie) {
    return this.http.post<Film>(this.url, film);
  }

  modifier(id: number, film: FilmSaisie) {
    return this.http.put<Film>(`${this.url}/${id}`, film);
  }

  supprimer(id: number) {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  associerActeur(filmId: number, acteurId: number) {
    return this.http.post<void>(`${this.url}/${filmId}/acteurs/${acteurId}`, null);
  }

  dissocierActeur(filmId: number, acteurId: number) {
    return this.http.delete<void>(`${this.url}/${filmId}/acteurs/${acteurId}`);
  }

}