package or.polytech.filmapi.Service;

import java.util.Collection;
import or.polytech.filmapi.Model.Film;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import or.polytech.filmapi.Model.Acteur;
import or.polytech.filmapi.Repository.FilmRepository;
import or.polytech.filmapi.utils.FilmNotFoundException;
import or.polytech.filmapi.utils.ActeurNotFoundException;
import or.polytech.filmapi.Repository.ActeurRepository;

@Service
public class FilmService {
    
    private final FilmRepository filmRepository;
    private final ActeurRepository acteurRepository;

    public FilmService(FilmRepository filmRepository, ActeurRepository acteurRepository) {
        this.filmRepository = filmRepository;
        this.acteurRepository = acteurRepository;
    }

    public Film saveFilm(Film film) {
            return filmRepository.save(film);
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

    public Film getFilmWithActeurs(Long id) {
        return filmRepository.findByIdWithActeur(id)
                .orElseThrow(() -> new FilmNotFoundException("aucun film: " + id));
    }

    @Transactional
    public void ajouterActeur(Long filmId, Long acteurId) {
        Film film = getFilmWithActeurs(filmId);
        Acteur acteur = acteurRepository.findById(acteurId)
                .orElseThrow(() -> new ActeurNotFoundException("aucun acteur: " + acteurId));
        film.getActeurs().add(acteur);
    }

    @Transactional
    public void retirerActeur(Long filmId, Long acteurId) {
        Film film = getFilmWithActeurs(filmId);
        film.getActeurs().removeIf(a -> a.getId().equals(acteurId));
    }

}