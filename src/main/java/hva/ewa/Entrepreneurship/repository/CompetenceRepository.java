package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.Competence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompetenceRepository extends CrudRepository<Competence, Integer> {

    @Query("SELECT competence_id, competence, competence_type FROM Competence")
    List<Competence> findAllCompetences(Integer competence_id, String competence, String competence_type);

    @Query("SELECT competence_id, competence, competence_type FROM Competence WHERE competence_type = 'sub'")
    List<Competence> findAllSubCompetences(Integer competence_id, String competence, String competence_type);

    @Query("SELECT competence_id, competence, competence_type FROM Competence WHERE competence_type = 'main'")
    List<Competence> findAllMainCompetences(Integer competence_id, String competence, String competence_type);
}
