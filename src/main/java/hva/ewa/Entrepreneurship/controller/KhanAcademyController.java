package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.model.KhanAcademyVideo;
import hva.ewa.Entrepreneurship.repository.KhanAcademyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class KhanAcademyController {

    @Autowired
    private KhanAcademyRepository khanAcademyRepository;

    public KhanAcademyController(KhanAcademyRepository khanAcademyRepository) {
        this.khanAcademyRepository = khanAcademyRepository;
    }

    /**
     *save video retrieved from external api Khan Academy into database.
     *
     * @param khanAcademyVideo
     * @return response with http status when video is successfully saved in database
     */
    @RequestMapping(method = RequestMethod.POST, value = "/khanacademy/video")
    public ResponseEntity insertVideos(@RequestBody KhanAcademyVideo[] khanAcademyVideo) {

        for (KhanAcademyVideo video : khanAcademyVideo) {
            khanAcademyRepository.save(video);
        }

        if (khanAcademyVideo.length == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(khanAcademyVideo, HttpStatus.OK);
    }

    /**
     *get a list of all videos.
     *
     * @param khanAcademyVideo
     * @return response with http status when retrieval of videos is successful
     */
    @RequestMapping(method = RequestMethod.GET, value = "/khanacademy/videolist")
    public ResponseEntity videoList(KhanAcademyVideo khanAcademyVideo) {

        List<KhanAcademyVideo> khanAcademyVideoList = khanAcademyRepository.getAllVideos(
                khanAcademyVideo.getId(),
                khanAcademyVideo.getUnique_id(),
                khanAcademyVideo.getTitle(),
                khanAcademyVideo.getDescription(),
                khanAcademyVideo.getImage(),
                khanAcademyVideo.getUrl(),
                khanAcademyVideo.getShow_on_top(),
                khanAcademyVideo.getShow_hide(),
                khanAcademyVideo.getCompetences(),
                khanAcademyVideo.getDeleted());

        return new ResponseEntity<>(khanAcademyVideoList, HttpStatus.OK);
    }

    /**
     *update video by id.
     *
     * @param khanAcademyVideo
     * @param video_id
     * @return response with http status when video is successfully updated
     */
    @PutMapping(value = "/khanacademy/update/{video_id}")
    public ResponseEntity<KhanAcademyVideo> updateVideoKhan(@RequestBody KhanAcademyVideo khanAcademyVideo, @PathVariable("video_id") Integer video_id) {

        KhanAcademyVideo video = khanAcademyRepository.findVideoById(video_id);

        video.setShow_on_top(khanAcademyVideo.getShow_on_top());
        video.setShow_hide(khanAcademyVideo.getShow_hide());
        video.setCompetences(khanAcademyVideo.getCompetences());

        khanAcademyRepository.save(video);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *update delete attribute of video when it is requested to be deleted from list.
     *
     * @param khanAcademyVideo
     * @param video_id
     * @return response with http status when the delete attribute of video is successfully updated
     */
    @PutMapping(value = "/khanacademy/delete/{video_id}")
    public ResponseEntity<KhanAcademyVideo> deleteVideoKhan(@RequestBody KhanAcademyVideo khanAcademyVideo, @PathVariable("video_id") Integer video_id) {

        KhanAcademyVideo video = khanAcademyRepository.findVideoById(video_id);

        video.setDeleted(khanAcademyVideo.getDeleted());

        khanAcademyRepository.save(video);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *get a list of videos linked to the competences that user has scored the lowest on.
     *
     * @param user_id
     * @return response with http status of a successful retrieval of videos linked to a specific set of competences from user
     */
    @RequestMapping(method = RequestMethod.GET, value = "/khanacademy/videolist/{user_id}")
    public ResponseEntity personalListUser(@PathVariable("user_id") Integer user_id) {

        List<String> threeCompetences = khanAcademyRepository.getThreeLowestCompetences(user_id);

        List<KhanAcademyVideo> khanAcademyVideoList = khanAcademyRepository.videoMatchLowestCompetences(threeCompetences.get(0), threeCompetences.get(1), threeCompetences.get(2));

        return new ResponseEntity<>(khanAcademyVideoList, HttpStatus.OK);
    }
}
