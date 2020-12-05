package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.repository.QuestionRepository;
import hva.ewa.Entrepreneurship.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     *get a list of shuffled questions.
     *
     * @param question
     * @return response with http status of a successful retrieval of shuffled questions
     */
    @RequestMapping(method = RequestMethod.GET, value = "/questions/shuffled-questions")
    public ResponseEntity getAllShuffledQuestions(Question question) {

        // Get a list of questions with a maximum of 4 questions linked to the same competence
        List<Question> questionList = questionRepository.findMaxTestQuestions(question.getQuestion_id(), question.getDescription_text(), question.getCompetence_id());

        // Shuffle list of questions
        Collections.shuffle(questionList);

        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    /**
     *get a list of all the questions.
     *
     * @param question
     * @return response with http status of a successful retrieval of questions
     */
    @RequestMapping(method = RequestMethod.GET, value = "/questions")
    public ResponseEntity getAllQuestions(Question question) {

        List<Question> questionList = questionRepository.findAllQuestions(question.getQuestion_id(), question.getDescription_text(), question.getCompetence_id());

        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    /**
     *save newly added questions to database.
     *
     * @param question
     * @return response with http status when question is successfully saved to database.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/questions")
    public ResponseEntity createQuestion(@RequestBody Question question) {

        questionRepository.save(question);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     *update question by id.
     *
     * @param question
     * @param question_id
     * @return response with http status when question is successfully updated or when question that is requested to be updated can't be found
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/questions/question/{question_id}")
    public ResponseEntity updateQuestion(@RequestBody Question question, @PathVariable("question_id") Integer question_id) {

        Question currentQuestion = questionRepository.findQuestionById(question_id);

        if (currentQuestion == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        questionRepository.updateQuestion(question.getQuestion_id(), question.getCompetence_id());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *delete requested question.
     *
     * @param question
     * @return response with http status when question is successfully deleted.
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/questions")
    public ResponseEntity deleteQuestion(@RequestBody Question question) {

        questionRepository.delete(question);

        return new ResponseEntity(HttpStatus.OK);
    }
}
