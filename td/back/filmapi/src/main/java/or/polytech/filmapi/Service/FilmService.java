package or.polytech.filmapi.Service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import or.polytech.filmapi.Model.Film;
import or.polytech.filmapi.Repository.FilmRepository;
import or.polytech.filmapi.utils.FilmNotFoundException;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public Film saveFilm(Film film) {
        if (film != null && film.getTitre() != null && !film.getTitre().isBlank()) {
            return filmRepository.save(film);
        }
        return null;
    }

    public Film getFilmById(Long id) throws FilmNotFoundException {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFoundException("aucun film: " + id);
        }
        return film;
    }

    public void deleteFilm(Long id) throws FilmNotFoundException {
        if (id != null && filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new FilmNotFoundException("aucun film: " + id);
        }
    }

    public Collection<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    public Film updateFilm(Long id, Film film) throws FilmNotFoundException {
        if (id != null && film != null && filmRepository.existsById(id)) {
            film.setId(id);
            return filmRepository.save(film);
        }
        throw new FilmNotFoundException("aucun film: " + id);
    }
}
