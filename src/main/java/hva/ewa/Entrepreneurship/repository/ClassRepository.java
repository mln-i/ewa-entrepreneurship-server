package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.Class;
import hva.ewa.Entrepreneurship.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClassRepository extends CrudRepository<Class, Integer> {

    @Query("SELECT c FROM Class c WHERE c.class_id = :class_id")
    Class findClassById(@Param("class_id") Integer class_id);

    @Query("SELECT CASE WHEN count(c) > 0 THEN TRUE ELSE FALSE END FROM Class c WHERE c.class_name = ?1")
    boolean doesClassExists(String class_name);

    @Query("SELECT c FROM Class c WHERE c.user_id = ?1")
    List<Class> listAllClasses(@PathVariable("user_id") Integer user_id);

    @Query("SELECT u FROM User u WHERE u.class_name = ?1")
    List<User> findUserByClass(String class_name);

    @Transactional
    @Modifying
    @Query("UPDATE Class c SET c.class_name = ?1 WHERE c.class_id = ?2")
    Integer updateClass(String class_name, @PathVariable("class_id") Integer class_id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.class_name = ?1 WHERE u.id = ?2")
    Integer updateClassOfUser(String class_name, Integer id);

}
