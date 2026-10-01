package or.polytech.filmapi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import or.polytech.filmapi.Model.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("""
            select f from Film f
            left join fetch f.acteurs
            where f.id = :id
            """)
    Optional<Film> findByIdWithActeur(Long id);
}

