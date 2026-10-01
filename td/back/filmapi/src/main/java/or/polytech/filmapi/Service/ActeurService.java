package or.polytech.filmapi.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import or.polytech.filmapi.Model.Acteur;
import or.polytech.filmapi.Model.Film;
import or.polytech.filmapi.Repository.ActeurRepository;
import or.polytech.filmapi.Repository.FilmRepository;
import or.polytech.filmapi.utils.ActeurNotFoundException;

@Service
public class ActeurService {

    private final ActeurRepository acteurRepository;
    private final FilmRepository filmRepository;

    public ActeurService(ActeurRepository acteurRepository, FilmRepository filmRepository) {
        this.acteurRepository = acteurRepository;
        this.filmRepository = filmRepository;
    }

    public List<Acteur> getAllActeurs() {
        return acteurRepository.findAll();
    }

    public Acteur getActeurById(Long id) {
        return acteurRepository.findById(id)
                .orElseThrow(() -> new ActeurNotFoundException("aucun acteur: " + id));
    }

    public Acteur saveActeur(Acteur acteur) {
        return acteurRepository.save(acteur);
    }

    public Acteur updateActeur(Long id, Acteur data) {
        Acteur acteur = getActeurById(id);
        acteur.setNom(data.getNom());
        acteur.setPrenom(data.getPrenom());
        acteur.setDateNaissance(data.getDateNaissance());
        return acteurRepository.save(acteur);
    }

    @Transactional
    public void deleteActeur(Long id) {
        Acteur acteur = getActeurById(id);
        for (Film film : acteur.getFilms()) {
            film.getActeurs().remove(acteur);
        }
        acteurRepository.delete(acteur);
    }

    public List<Film> getFilmsDeLActeur(Long id) {
        getActeurById(id);
        return filmRepository.findByActeursId(id);
    }
}