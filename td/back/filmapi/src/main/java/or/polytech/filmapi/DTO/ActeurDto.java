package or.polytech.filmapi.DTO;

import java.time.LocalDate;

public record ActeurDto(
    Long id, 
    String nom, 
    String prenom,
    LocalDate dateNaissance) {

}
