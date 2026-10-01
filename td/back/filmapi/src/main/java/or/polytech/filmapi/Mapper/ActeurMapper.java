package or.polytech.filmapi.Mapper;

import or.polytech.filmapi.DTO.ActeurCreationDto;
import or.polytech.filmapi.DTO.ActeurDto;
import or.polytech.filmapi.Model.Acteur;

public class ActeurMapper {
    public static ActeurDto toDto(Acteur acteur) {
        return new ActeurDto(
            acteur.getId(),
            acteur.getNom(),
            acteur.getPrenom(),
            acteur.getDateNaissance()
        );
    }

    public static Acteur toEntity(ActeurCreationDto d) {
        Acteur a = new Acteur();
        a.setNom(d.nom());
        a.setPrenom(d.prenom());
        a.setDateNaissance(d.dateNaissance());
        return a;
    }
}
