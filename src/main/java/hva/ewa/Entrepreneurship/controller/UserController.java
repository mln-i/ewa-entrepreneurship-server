package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.repository.UserRepository;
import hva.ewa.Entrepreneurship.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * get a list of all users.
     *
     * @param user
     * @return response with http status of a succesful retrieval of all users
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity retrieveAllUsers(User user) {

        List<User> usersList = userRepository.listAllUsers(user.getId(), user.getEmail(), user.getFirst_name(), user.getLast_name(), user.getRole(), user.getTeacher(), user.getClass_name());

        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    /**
     * get a list of students that are attending the same class.
     *
     * @param class_name
     * @return response with http status of a succesful retrieval of students from the same class
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users/class/{class_name}")
    public ResponseEntity retrieveAllUsersOfSameClass(@PathVariable("class_name") String class_name) {

        List<User> usersFromSameClassList = userRepository.listAllUsersBySameClass(class_name);

        return new ResponseEntity(usersFromSameClassList, HttpStatus.OK);
    }

    /**
     * get a list of teachers and their classes.
     *
     * @return response with http status of a succesful retrieval of all teachers and their classes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users/list/class/list")
    public ResponseEntity retrieveAllTeachersAndClasses() {

        List<User> teachersAndClassesList = userRepository.listAllTeachersAndClasses();

        return new ResponseEntity(teachersAndClassesList, HttpStatus.OK);
    }

    /**
     * save a new user into database.
     *
     * @param user
     * @return response with http status when user is succesfully saved into database or if user already exists.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ResponseEntity<Void> createUser(@RequestBody User user) {

        if (userRepository.doesUserExist(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * get user by their id.
     *
     * @param id
     * @return response with http status of a succesful retrieval of user or an unsuccessful retrieval if user can't be found
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users/user/{userid}")
    public ResponseEntity<?> getUser(@PathVariable("userid") Integer id) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * update a specific user by their id.
     *
     * @param updateUser
     * @param userid
     * @return response with http status when user is succesfully updated.
     */
    @PutMapping(value = "/users/user/{userid}")
    public ResponseEntity<User> updateUser(@RequestBody User updateUser, @PathVariable("userid") Integer userid) {

        User user = userRepository.findUserById(userid);

        user.setEmail(updateUser.getEmail());
        user.setFirst_name(updateUser.getFirst_name());
        user.setLast_name(updateUser.getLast_name());
        user.setRole(updateUser.getRole());

        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * update the class to which a user belongs to when changes to the class are detected.
     *
     * @param updateUser
     * @param userid
     * @return response with http status of a succesful update or unsuccesful update if the user can't be found
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/users/user/{userid}/class")
    public ResponseEntity updateClassOfUser(@RequestBody User updateUser, @PathVariable("userid") Integer userid) {

        User user = userRepository.findUserById(userid);

        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        user.setClass_name(updateUser.getClass_name());
        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * delete a user by their id.
     *
     * @param userid
     * @return a response with http status of a succesful delete request
     */
    @DeleteMapping(value = "/users/user/{userid}")
    public ResponseEntity<User> deleteUser(@PathVariable("userid") Integer userid) {

        User user = userRepository.findUserById(userid);

        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Can be removed
    @RequestMapping(method = RequestMethod.GET, value = "/users/emails")
    public List<User> existingEmail(User user) {

        return userRepository.findAllEmail(user.getEmail());
    }

    /**
     * get id from a specific user by their email.
     *
     * @param email
     * @return response with http status of a succesful retrieval of an user and the id of the user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users/user/id/{email}")
    public ResponseEntity findUserIdBasedOnEmail(@PathVariable("email") String email) {

        Integer user_id = userRepository.findByUserEmail(email).getId();

        return new ResponseEntity<>(user_id, HttpStatus.OK);
    }
}
