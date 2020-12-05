package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.repository.CompetenceRepository;
import hva.ewa.Entrepreneurship.model.Competence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CompetenceController {

    @Autowired
    private CompetenceRepository competenceRepository;

    /**
     * get a list of all competences.
     *
     * @param competence
     * @return response with http status of a successful retrieval of list of competences
     */
    @RequestMapping(method = RequestMethod.GET, value = "/competences")
    public ResponseEntity getAllCompetences(Competence competence) {

        List<Competence> competenceList = competenceRepository.findAllCompetences(competence.getCompetence_id(), competence.getCompetence(), competence.getCompetence_type());

        return new ResponseEntity<>(competenceList, HttpStatus.OK);
    }

    /**
     * get a list of all sub-competences
     *
     * @param competence
     * @return response with http status of a successful retrieval of a list of sub-competences
     */
    @RequestMapping(method = RequestMethod.GET, value = "/competences/sub-competences")
    public ResponseEntity getAllSubCompetences(Competence competence) {

        List<Competence> subCompetenceList = competenceRepository.findAllSubCompetences(competence.getCompetence_id(), competence.getCompetence(), competence.getCompetence_type());

        return new ResponseEntity<>(subCompetenceList, HttpStatus.OK);
    }

    /**
     * get a list of all main-competences
     *
     * @param competence
     * @return response with http status of a successful retrieval of a list of main-competences
     */
    @RequestMapping(method = RequestMethod.GET, value = "/competences/main-competences")
    public ResponseEntity getAllMainCompetences(Competence competence) {

        List<Competence> mainCompetenceList = competenceRepository.findAllMainCompetences(competence.getCompetence_id(), competence.getCompetence(), competence.getCompetence_type());

        return new ResponseEntity<>(mainCompetenceList, HttpStatus.OK);
    }
}
