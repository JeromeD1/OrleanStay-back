package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.request.PhotoListSaveRequest;
import com.formation.orleanStay.models.request.PhotoSaveRequest;
import com.formation.orleanStay.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
@Slf4j
public class PhotoController {

    final private PhotoService photoService;

    public PhotoController(PhotoService photoService){
        this.photoService = photoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> findAll(){
        log.debug("Fetching all photo of all appartments");
        return photoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDTO findById(@PathVariable Long id){
        log.debug("Fetching photo with id = {}", id);
        return photoService.findDTOById(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PhotoDTO create(@RequestBody PhotoSaveRequest photoSaveRequest) {
//        log.debug("Add new photo with value : {}", photoSaveRequest);
//        return photoService.create(photoSaveRequest);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDTO> create(@RequestBody PhotoSaveRequest photoSaveRequest) {
        log.debug("Add new photo with value : {}", photoSaveRequest);
        return photoService.create(photoSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDTO update(@PathVariable Long id, @RequestBody PhotoSaveRequest photoSaveRequest) {
        log.debug("Updating photo of id {} with value {}", id, photoSaveRequest);
        return photoService.update(id, photoSaveRequest);
    }

    @PutMapping("/updateOrder/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> udpateOrder(@PathVariable Long appartmentId, @RequestBody PhotoListSaveRequest photosToReorder){
        log.debug("Updating order of photos with appartementId {} and values {}", appartmentId, photosToReorder);
        return photoService.updateOrder(appartmentId, photosToReorder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.debug("Deleting photo with id {}", id);
        photoService.delete(id);
    }
}
