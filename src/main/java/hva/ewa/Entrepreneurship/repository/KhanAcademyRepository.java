package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.KhanAcademyVideo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhanAcademyRepository extends CrudRepository<KhanAcademyVideo, Integer> {

    @Query("SELECT id, unique_id, title, description, image, url, show_on_top, show_hide, competences, deleted FROM KhanAcademyVideo ")
    List<KhanAcademyVideo> getAllVideos(Integer id, String unique_id, String title, String description,
                                        String image, String url, Integer show_on_top, Integer show_hide,
                                        String competences, Integer deleted);

    @Query("SELECT u FROM KhanAcademyVideo u WHERE u.id = :video_id")
    KhanAcademyVideo findVideoById(@Param("video_id") Integer video_id);

    @Query(nativeQuery = true, value = "SELECT c.competence FROM Result r INNER JOIN Competence c ON r.competence_id = c.competence_id " +
            "WHERE date_finished =(SELECT MAX(date_finished)FROM result r2 WHERE user_id = :user_id " +
            "AND competence_id > 3 ORDER BY score DESC) ORDER BY r.score ASC LIMIT 3")
    List<String> getThreeLowestCompetences(@Param("user_id") Integer user_id);

    @Query("SELECT u FROM KhanAcademyVideo u WHERE (competences = ?1 OR competences = ?2 OR competences = ?3) AND show_hide = 1")
    List<KhanAcademyVideo> videoMatchLowestCompetences(String competence1, String competence2, String competence3);
}


