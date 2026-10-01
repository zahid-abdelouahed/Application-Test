package or.polytech.filmapi.Controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import or.polytech.filmapi.Service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<FilmDto> films() {
        return service.getAllFilms();
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        return service.getFilmById(id);
    }

    @PutMapping("/films/{id}")
    public FilmDto updateFilm(@PathVariable Long id,@Valid @RequestBody Film film) {
        return service.updateFilm(id, film);
    }

    @DeleteMapping("/films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Long id) {
        service.deleteFilm(id);
    }
    
    @GetMapping()
    @PostMapping("/films")
    public ResponseEntity<FilmDto> addFilm(@Valid @RequestBody Film film) {
        FilmDto filmCree = service.saveFilm(film);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(FilmCree.getId()).toUri();
        return ResponseEntity.created(uri).body(FilmCree);
    }


}
