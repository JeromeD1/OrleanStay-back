package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.request.PhotoListSaveRequest;
import com.formation.orleanStay.models.request.PhotoSaveRequest;
import com.formation.orleanStay.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        log.info("Fetching all photo of all appartments");
        return photoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDTO findById(@PathVariable Long id){
        log.info("Fetching photo with id = {}", id);
        return photoService.findDTOById(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PhotoDTO create(@RequestBody PhotoSaveRequest photoSaveRequest) {
//        log.info("Add new photo with value : {}", photoSaveRequest);
//        return photoService.create(photoSaveRequest);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDTO> create(@RequestBody PhotoSaveRequest photoSaveRequest) {
        log.info("Add new photo with value : {}", photoSaveRequest);
        return photoService.create(photoSaveRequest);
    }

    @PutMapping("/{id}/oldImgId/{oldImgId}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDTO update(@PathVariable Long id, @PathVariable String oldImgId, @RequestBody PhotoSaveRequest photoSaveRequest) throws IOException {
        log.info("Updating photo of id {} with value {}", id, photoSaveRequest);
        return photoService.update(id, oldImgId, photoSaveRequest);
    }

    @PutMapping("/updateOrder/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> udpateOrder(@PathVariable Long appartmentId, @RequestBody PhotoListSaveRequest photosToReorder){
        System.out.println(photosToReorder);
        System.out.println("appartmentId : " + appartmentId);
        log.info("Updating order of photos with appartementId {} and values {}", appartmentId, photosToReorder);
        return photoService.updateOrder(appartmentId, photosToReorder);
    }

    @DeleteMapping("/{id}/imgId/{imgId}")
    public ResponseEntity<List<PhotoDTO>> delete(@PathVariable Long id, @PathVariable String imgId) throws IOException {
        log.info("Deleting photo with id {}", id);
        List<PhotoDTO> photos = photoService.delete(id, imgId);
        return ResponseEntity.ok(photos);
    }
}
