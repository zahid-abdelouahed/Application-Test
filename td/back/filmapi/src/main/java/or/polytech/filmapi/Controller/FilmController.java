package or.polytech.filmapi.Controller;

import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import or.polytech.filmapi.Service.FilmService;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import or.polytech.filmapi.Mapper.FilmMapper;
import or.polytech.filmapi.DTO.FilmDto;
import or.polytech.filmapi.DTO.FilmCreationDto;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<FilmDto> films() {
        return service.getAllFilms().stream().map(FilmMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        return  FilmMapper.toDto(service.getFilmById(id));
    }

    @PutMapping("/{id}")
    public FilmDto updateFilm(@PathVariable Long id,@Valid @RequestBody FilmCreationDto dto) {
        return FilmMapper.toDto(service.updateFilm(id, FilmMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Long id) {
        service.deleteFilm(id);
    }
    
    @PostMapping
    public ResponseEntity<FilmDto> addFilm(@Valid @RequestBody FilmCreationDto dto) {
        FilmDto filmCree = FilmMapper.toDto(service.saveFilm(FilmMapper.toEntity(dto)));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(filmCree.id()).toUri();
        return ResponseEntity.created(uri).body(filmCree);
    }


}
