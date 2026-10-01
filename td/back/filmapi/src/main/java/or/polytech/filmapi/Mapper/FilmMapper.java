package or.polytech.filmapi.Mapper;

import or.polytech.filmapi.Model.Film;
import or.polytech.filmapi.DTO.FilmCreationDto;
import or.polytech.filmapi.DTO.FilmDto;

public class FilmMapper {

    public static FilmDto toDto(Film film) {
        return new FilmDto(
            film.getId(),
            film.getTitre(),
            film.getRealisateur(),
            film.getDateSortie(),
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
