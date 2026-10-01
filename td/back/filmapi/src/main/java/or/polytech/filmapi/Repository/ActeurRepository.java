package or.polytech.filmapi.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import or.polytech.filmapi.Model.Acteur;

public interface ActeurRepository extends JpaRepository<Acteur, Long> {
   
}
