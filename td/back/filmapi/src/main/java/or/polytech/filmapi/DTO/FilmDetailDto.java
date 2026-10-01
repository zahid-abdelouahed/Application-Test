package or.polytech.filmapi.DTO;

import java.time.LocalDate;
import java.util.List;
import or.polytech.filmapi.utils.Genre;

public record FilmDetailDto(
    Long id,
    String titre,
    String realisateur,
    LocalDate dateSortie,
    Genre genre,
    List<ActeurDto> acteurs
) {}