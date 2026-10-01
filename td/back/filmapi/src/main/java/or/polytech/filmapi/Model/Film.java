package or.polytech.filmapi.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import or.polytech.filmapi.utils.Genre;
import jakarta.persistence.*;



@Entity
public class Film {
    @Column(nullable = false, length = 200)
    private String titre;
    private String realisateur;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateSortie;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @ManyToMany
    @JoinTable(name = "film_acteur",
    joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "acteur_id"))
    private Set<Acteur> acteurs = new HashSet<>();

    public Film() {
    }
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

    public Set<Acteur> getActeurs() {
        return acteurs;
    }

}
