import { inject, Injectable } from "@angular/core";
import { Film } from "../models/film.model";
import { Acteur } from "../models/acteur.model";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class ActeurService {
    private http = inject(HttpClient);
    private url = '/api/acteurs';
    
    getAll() {
        return this.http.get<Acteur[]>(this.url);
    }

    getById(id: number) {
        return this.http.get<Acteur>(`${this.url}/${id}`);
    }

    getFilms(id: number) {
        return this.http.get<Film[]>(`${this.url}/${id}/films`);
    }
}