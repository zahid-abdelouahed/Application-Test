package or.polytech.filmapi.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import or.polytech.filmapi.utils.Genre;

public record FilmDto(
    Long id,
    @NotBlank String titre,
    String realisateur,
    LocalDate dateSortie,
    Genre genre
) {}
