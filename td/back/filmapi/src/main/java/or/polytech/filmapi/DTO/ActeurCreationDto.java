package or.polytech.filmapi.DTO;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
public record ActeurCreationDto(
    @NotBlank String nom,
    String prenom,
    LocalDate dateNaissance
) {

}
