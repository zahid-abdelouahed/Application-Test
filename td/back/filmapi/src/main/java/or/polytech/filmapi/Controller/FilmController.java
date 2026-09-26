package or.polytech.filmapi.Controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import or.polytech.filmapi.Model.Film;
import or.polytech.filmapi.Service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.validation.Valid;

@RestController
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/films")
    public Collection<Film> films() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/films/{id}")
    public Film updateFilm(@PathVariable Long id,@Valid @RequestBody Film film) {
        return filmService.updateFilm(id, film);
    }

    @DeleteMapping("/films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
    }
    @PostMapping("/films")
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        Film FilmCree = filmService.saveFilm(film);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(FilmCree.getId()).toUri();
        return ResponseEntity.created(uri).body(FilmCree);
    }
}
