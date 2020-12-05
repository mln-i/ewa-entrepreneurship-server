package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.model.Result;
import hva.ewa.Entrepreneurship.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;
    private List<Result> resultList, scoreList;

    // Constuctor
    public ResultController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    /**
     * get a list of all the sets of results from a specific user by their id.
     *
     * @param user_id
     * @return response with http status when retrieval of all results from a specific user is successful or when user's results can't be found
     */
    @RequestMapping(method = RequestMethod.GET, value = "/results/user/{user_id}")
    public ResponseEntity<Void> getResults(@PathVariable("user_id") Integer user_id) {

        if (resultRepository.doesUserHasResult(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resultList = resultRepository.getAllResults(user_id);

        return new ResponseEntity(resultList, HttpStatus.OK);
    }

    /**
     * get an ordered list of scores from user's set of results by their id.
     *
     * @param user_id
     * @return response with http status when retrieval of scores from a specific set of results is successful or when user can't be found
     */
    @RequestMapping(method = RequestMethod.GET, value = "/results/scores/user/{user_id}")
    public ResponseEntity orderResultsScore(@PathVariable("user_id") Integer user_id) {

        if (resultRepository.doesUserHasResult(user_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        scoreList = resultRepository.getOrderedScores(user_id);

        return new ResponseEntity<>(scoreList, HttpStatus.OK);
    }

    /**
     * save results from the competence test made by user in database.
     *
     * @param result
     * @return response with http status when results are successfully saved to the database
     */
    @RequestMapping(method = RequestMethod.POST, value = "/results")
    public ResponseEntity<Void> insertScore(@RequestBody Result result) {

        resultRepository.save(result);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * get a list of dates of all the set of results from an user by their id.
     *
     * @param user_id
     * @return response with http status of a successful retrieval of dates of results from a specific user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/results/dates/user/{user_id}")
    public ResponseEntity<Void> getResultsDates(@PathVariable("user_id") Integer user_id) {

        List<String> dateList = resultRepository.getAllResultsDates(user_id);

        return new ResponseEntity(dateList, HttpStatus.OK);
    }

    /**
     * get a list of scores from a specific set of results that is selected by the user.
     *
     * @param user_id
     * @param date_finished
     * @return response with http status of a successful retrieval of specific set of results selected by the user.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/results/dates/date/{date_finished}/user/{user_id}")
    public ResponseEntity<Void> getSelectedTestResults(@PathVariable("user_id") Integer user_id, @PathVariable("date_finished") String date_finished) {

        resultList = resultRepository.getAllSelectedTestResults(user_id, date_finished);

        return new ResponseEntity(resultList, HttpStatus.OK);
    }
}
