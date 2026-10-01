package or.polytech.filmapi.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import or.polytech.filmapi.DTO.ActeurCreationDto;
import or.polytech.filmapi.DTO.ActeurDto;
import or.polytech.filmapi.DTO.FilmDto;
import or.polytech.filmapi.Mapper.ActeurMapper;
import or.polytech.filmapi.Mapper.FilmMapper;
import or.polytech.filmapi.Service.ActeurService;
import or.polytech.filmapi.Mapper.FilmMapper;
import or.polytech.filmapi.Mapper.ActeurMapper;

@RestController
@RequestMapping("/acteurs")
public class ActeurController {

    private final ActeurService service;

    public ActeurController(ActeurService service) {
        this.service = service;
    }

    @GetMapping
    public List<ActeurDto> acteurs() {
        return service.getAllActeurs().stream().map(ActeurMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ActeurDto getActeurById(@PathVariable Long id) {
        return ActeurMapper.toDto(service.getActeurById(id));
    }

    @PostMapping
    public ResponseEntity<ActeurDto> addActeur(@Valid @RequestBody ActeurCreationDto dto) {
        ActeurDto acteurCree = ActeurMapper.toDto(service.saveActeur(ActeurMapper.toEntity(dto)));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(acteurCree.id()).toUri();
        return ResponseEntity.created(uri).body(acteurCree);
    }

    @PutMapping("/{id}")
    public ActeurDto updateActeur(@PathVariable Long id, @Valid @RequestBody ActeurCreationDto dto) {
        return ActeurMapper.toDto(service.updateActeur(id, ActeurMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActeur(@PathVariable Long id) {
        service.deleteActeur(id);
    }

    @GetMapping("/{id}/films")
    public List<FilmDto> filmsDeLActeur(@PathVariable Long id) {
        return service.getFilmsDeLActeur(id).stream().map(FilmMapper::toDto).toList();
    }
}
