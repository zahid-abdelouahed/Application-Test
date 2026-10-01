package or.polytech.filmapi.DTO;

import java.time.LocalDate;
import or.polytech.filmapi.utils.Genre;

public record FilmDto(
    Long id,
    String titre,
    String realisateur,
    LocalDate dateSortie,
    Genre genre
) {}
