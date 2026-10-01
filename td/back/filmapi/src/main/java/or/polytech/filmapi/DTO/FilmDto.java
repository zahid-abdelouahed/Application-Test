package or.polytech.filmapi.DTO;

import java.time.LocalDate;

public record FilmDto(
    Long id,
    String titre,
    String realisateur,
    LocalDate dateSortie,
    String genre
) {}
