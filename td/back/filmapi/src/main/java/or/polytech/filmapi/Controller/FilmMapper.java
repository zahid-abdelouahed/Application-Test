package or.polytech.filmapi.Controller;

import or.polytech.filmapi.Model.Film;

public class FilmMapper {

    public static FilmDto toDto(Film film) {
        return new FilmDto(
                film.getId(),
                film.getTitle(),
                film.getDirector(),
                film.getReleaseYear(),
                film.getGenre()
        );
    }

    public static Film toEntity(FilmCreationDto d) {
        Film f = new Film();
        f.setTitre(f.titre());
        f.setRealisateur(f.realisateur());
        f.setDateSortie(f.DateSortie());
        f.setGenre(f.Genre());
        return f;
    }


}
