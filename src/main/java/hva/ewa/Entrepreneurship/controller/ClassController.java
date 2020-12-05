package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.model.Class;
import hva.ewa.Entrepreneurship.model.User;
import hva.ewa.Entrepreneurship.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ClassController {

    @Autowired
    private ClassRepository classRepository;

    /**
     * save new class to database.
     *
     * @param createdClass
     * @return response with http status when class is successfully saved or when class already exists
     */
    @RequestMapping(method = RequestMethod.POST, value = "/class")
    public ResponseEntity createClass(@RequestBody Class createdClass) {

        if (classRepository.doesClassExists(createdClass.getClass_name())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        classRepository.save(createdClass);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * update changes to class.
     *
     * @param createdClass
     * @param class_id
     * @return response with http status when class is successfully updated or when class can't be found
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/class/{class_id}")
    public ResponseEntity updateClassOfUser(@RequestBody Class createdClass, @PathVariable("class_id") Integer class_id) {

        Class currentClass = classRepository.findClassById(class_id);
        List<User> listOfUsersInCurrentClass = classRepository.findUserByClass(currentClass.getClass_name());

        if (currentClass == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Update any students belonging to class that is updated
        for (int i = 0; i < listOfUsersInCurrentClass.size(); i++) {
            listOfUsersInCurrentClass.get(i).setClass_name(createdClass.getClass_name());
            classRepository.updateClassOfUser(createdClass.getClass_name(), listOfUsersInCurrentClass.get(i).getId());
        }

        currentClass.setClass_name(createdClass.getClass_name());
        classRepository.updateClass(createdClass.getClass_name(), class_id);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * get a list of classes from a specific teacher by their id.
     *
     * @param user_id
     * @return response with http status of a successful retrieval of classes from a specific teacher
     */
    @RequestMapping(method = RequestMethod.GET, value = "/class/list/teacher/{user_id}")
    public ResponseEntity getAllClassesOfTeacher(@PathVariable("user_id") Integer user_id) {

        List<Class> classList = classRepository.listAllClasses(user_id);

        return new ResponseEntity(classList, HttpStatus.OK);
    }

}
