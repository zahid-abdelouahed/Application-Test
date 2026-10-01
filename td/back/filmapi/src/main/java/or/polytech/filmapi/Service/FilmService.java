package or.polytech.filmapi.Service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import or.polytech.filmapi.Model.Film;
import or.polytech.filmapi.Repository.FilmRepository;
import or.polytech.filmapi.utils.FilmNotFoundException;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film saveFilm(Film film) {
        if (film != null && film.getTitre() != null && !film.getTitre().isBlank()) {
            return filmRepository.save(film);
        }
        return null;
    }

    public Film getFilmById(Long id) throws FilmNotFoundException {
        if (id != null && filmRepository.existsById(id)) {
            return filmRepository.findById(id).get();
        }
        throw new FilmNotFoundException("aucun film: " + id);
    }

    public void deleteFilm(Long id) throws FilmNotFoundException {
        if (id != null && filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
        } else {
            throw new FilmNotFoundException("aucun film: " + id);
        }
    }

    public Collection<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film updateFilm(Long id, Film data) throws FilmNotFoundException {
        Film film = getFilmById(id);
        film.setTitre(data.getTitre());
        film.setRealisateur(data.getRealisateur());
        film.setDateSortie(data.getDateSortie());
        film.setGenre(data.getGenre());
        return filmRepository.save(film);
    }
}