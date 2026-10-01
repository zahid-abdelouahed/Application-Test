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
        f.setTitre(d.titre());
        f.setRealisateur(d.realisateur());
        f.setDateSortie(d.dateSortie());
        f.setGenre(d.genre());
        return f;
    }


}
