package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

    @Query(nativeQuery = true, value = "(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 4 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 5 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 6 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 7 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 8 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 9 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 10 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 11 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 12 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 13 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 14 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 15 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 16 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 17 LIMIT 4)" +
            "UNION ALL(SELECT question_id, description_text, competence_id FROM Question WHERE competence_id = 18 LIMIT 4)")
    List<Question> findMaxTestQuestions(Integer question_id, String description_text, Integer competence_id);

    @Query("SELECT question_id, description_text, competence_id FROM Question")
    List<Question> findAllQuestions(Integer question_id, String description_text, Integer competence_id);

    @Transactional
    @Modifying
    @Query("UPDATE Question q SET q.competence_id = ?2 WHERE q.question_id = ?1")
    Integer updateQuestion(@PathVariable("question_id") Integer question_id, Integer competence_id);

    @Query("SELECT q FROM Question q WHERE q.question_id = :question_id")
    Question findQuestionById(@Param("question_id") Integer question_id);

}
