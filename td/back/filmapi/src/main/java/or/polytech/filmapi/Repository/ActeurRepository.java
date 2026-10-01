package or.polytech.filmapi.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import or.polytech.filmapi.Model.Acteur;
import org.springframework.stereotype.Repository;
import or.polytech.filmapi.Model.Film;

public interface ActeurRepository extends JpaRepository<Acteur, Long> {

    List<Acteur> findByFilmsId(Long filmId);

    @Query("select a from Acteur a join a.films f where f.id = :filmId")
    List<Acteur> findActeursDuFilm(Long filmId);
}
