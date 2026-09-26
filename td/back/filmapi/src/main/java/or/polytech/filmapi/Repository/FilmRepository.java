package or.polytech.filmapi.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import or.polytech.filmapi.Model.Film;

@Repository 
public class FilmRepository {

    private final Map<Long, Film> films = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Film save(Film film) {
        if (film.getId() == null) {
            sequence.incrementAndGet();
            film.setId(sequence.get());
        }
        films.put(film.getId(), film);
        return film;
    }

    public Film findById(Long id) {
        return films.get(id);
    }

    public void deleteById(Long id) {
        films.remove(id);
    }

    public Collection<Film> getAllFilms() {
            return films.values();
    }

    public boolean existsById(Long id) {
        return films.get(id) != null;
    }
     
    
}
