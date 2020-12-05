package hva.ewa.Entrepreneurship.repository;

import hva.ewa.Entrepreneurship.model.OpenLearningVideo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OpenLearningVideoRepository extends CrudRepository<OpenLearningVideo, Integer> {

    @Query("SELECT v FROM OpenLearningVideo v WHERE v.id = :openlearningvideo_id")
    OpenLearningVideo findVideoById(@Param("openlearningvideo_id") Integer id);

    @Query("SELECT v FROM OpenLearningVideo v")
    List<OpenLearningVideo> listAllVideos();

    @Transactional
    @Modifying
    @Query("UPDATE OpenLearningVideo v SET v.show_on_top = ?1, v.show_hide = ?2, v.competences = ?3 WHERE v.id = ?4")
    Integer updateOpenLearningVideo(Integer show_on_top, Integer show_hide, String competences, @PathVariable("openlearningvideo_id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE OpenLearningVideo v SET v.deleted = ?1 WHERE v.id = ?2")
    Integer updateDeletedOpenLearningVideo(Integer deleted, @PathVariable("openlearningvideo_id") Integer id);
}
