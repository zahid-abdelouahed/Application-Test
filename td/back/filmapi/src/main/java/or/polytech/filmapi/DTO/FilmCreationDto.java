package or.polytech.filmapi.DTO;
public record FilmCreationDto(
    String titre,
    String realisateur,
    LocalDate dateSortie,
    String genre
) {}
