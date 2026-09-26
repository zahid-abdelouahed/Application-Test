package or.polytech.filmapi.Model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

import or.polytech.filmapi.utils.Genre;

public class Film {
    private String titre;
    private String realisateur;
    private Long id;
    private LocalDate dateSortie;
    private Genre genre;

    public Film() {
    }
	@NotBlank
    public String getTitre() {
        return titre;
    }   
    
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    public String getRealisateur() {
        return realisateur;
    }   

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
