import { Component, computed, input, output } from '@angular/core';
import { Film } from '../../models/film.model';
import { RouterLink } from '@angular/router';
import { DatePipe, NgClass } from '@angular/common';

@Component({
  imports: [RouterLink, DatePipe, NgClass],
  selector: 'app-film-card',
  styleUrl: './film-card.css',
  templateUrl: './film-card.html',
})
export class FilmCard {
  
  film = input.required<Film>();
  supprimer = output<Film>();

  estAncien = computed(() => {
    const date = this.film().dateSortie;
    return date !== null && new Date(date).getFullYear() < 2000;
  });

  onSupprimer() {
    this.supprimer.emit(this.film());
  }
}
