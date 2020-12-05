package hva.ewa.Entrepreneurship.controller;

import hva.ewa.Entrepreneurship.model.AdditionalVideos;
import hva.ewa.Entrepreneurship.repository.AdditionalVideosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AdditionalVideosController {

    @Autowired
    private AdditionalVideosRepository additionalVideosRepository;

    public AdditionalVideosController(AdditionalVideosRepository additionalVideosRepository) {
        this.additionalVideosRepository = additionalVideosRepository;
    }

    // This method will be used to save video
    @RequestMapping(method = RequestMethod.POST, value = "/additionalvideo/video/insert/")
    public ResponseEntity insertVideo(@RequestBody AdditionalVideos additionalVideos) {

        additionalVideosRepository.save(additionalVideos);

        if (additionalVideos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(additionalVideos, HttpStatus.OK);
    }

    // Get method this method uses the repository to retrieve all the videos from the database and
    // store it in a list.
    @RequestMapping(method = RequestMethod.GET, value = "/additionalvideos/list")
    public ResponseEntity getAllVideos(AdditionalVideos additionalVideos) {

        List<AdditionalVideos> listOfAdditionalVideos = additionalVideosRepository.getAllVideos(
                additionalVideos.getId_video(), additionalVideos.getTitle(), additionalVideos.getDescription()
                , additionalVideos.getUrl(), additionalVideos.getCompetence(), additionalVideos.getUser_id());

        return new ResponseEntity<>(listOfAdditionalVideos, HttpStatus.OK);
    }

    /**
     * update video by their id.
     *
     * @param additionalVideos
     * @param id_video
     * @return response with http status when video is successfully updated
     */
    @PutMapping(value = "/additionalvideo/video/update/{id_video}")
    public ResponseEntity<AdditionalVideos> updateAdditionalVideos(@RequestBody AdditionalVideos additionalVideos, @PathVariable("id_video") Integer id_video) {

        AdditionalVideos video = additionalVideosRepository.getVideoFromId(id_video);

        video.setTitle(additionalVideos.getTitle());
        video.setDescription(additionalVideos.getDescription());
        video.setUrl(additionalVideos.getUrl());
        video.setCompetence(additionalVideos.getCompetence());

        additionalVideosRepository.save(video);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * delete video by id.
     *
     * @param id_video
     * @return response with http status when video is successfully deleted
     */
    @DeleteMapping(value = "/additionalvideo/delete/{id_video}")
    public ResponseEntity<AdditionalVideos> deleteUser(@PathVariable("id_video") Integer id_video) {

        AdditionalVideos video = additionalVideosRepository.getVideoFromId(id_video);

        additionalVideosRepository.delete(video);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


