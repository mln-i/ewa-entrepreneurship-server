package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Integer> {

    @Query("SELECT CASE WHEN count(u) <= 0 THEN TRUE ELSE FALSE END FROM Result u WHERE u.user_id = ?1")
    boolean doesUserHasResult(Integer user_id);

    @Query("SELECT r1.user_id, r1.competence_id, r1.score, r1.date_finished FROM Result r1 WHERE date_finished = (SELECT MAX(date_finished) \n" +
            "FROM Result r2 WHERE user_id = :user_id) ORDER BY competence_id ASC")
    List<Result> getAllResults(@Param("user_id") Integer id);

    @Query("SELECT r1.user_id, r1.competence_id, r1.score, r1.date_finished FROM Result r1 WHERE date_finished = (SELECT MAX(date_finished) \n" +
            "FROM Result r2 WHERE user_id = :user_id) AND competence_id > 3 ORDER BY score DESC")
    List<Result> getOrderedScores(@Param("user_id") Integer id);

    @Query("SELECT DISTINCT date_finished FROM Result WHERE user_id = :user_id ORDER BY date_finished DESC")
    List<String> getAllResultsDates(@Param("user_id") Integer id);

    @Query("SELECT user_id, competence_id, score, date_finished FROM Result WHERE user_id = ?1 AND date_finished = ?2 ORDER BY competence_id ASC")
    List<Result> getAllSelectedTestResults(@Param("user_id") Integer id, @Param("date_finished") String date_finished);
}
