package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.DTO.PhotoDTO;
import com.formation.formationAPI.models.request.PhotoListSaveRequest;
import com.formation.formationAPI.models.request.PhotoSaveRequest;
import com.formation.formationAPI.service.PrivatePhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/privatePhoto")
public class PrivatePhotoController {
    final private PrivatePhotoService privatePhotoService;

    public PrivatePhotoController(PrivatePhotoService privatePhotoService) {
        this.privatePhotoService = privatePhotoService;
    }

    @GetMapping("/appartment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> findByAppartmentId(@PathVariable Long id){
        log.debug("Fetching private photos with appartment_id = {}", id);
        return privatePhotoService.findByAppartmentId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<PhotoDTO> create(@RequestBody PhotoSaveRequest photoSaveRequest) {
        log.debug("Add new private photo with value : {}", photoSaveRequest);
        return privatePhotoService.create(photoSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDTO update(@PathVariable Long id, @RequestBody PhotoSaveRequest photoSaveRequest) {
        log.debug("Updating private photo of id {} with value {}", id, photoSaveRequest);
        return privatePhotoService.update(id, photoSaveRequest);
    }

    @PutMapping("/updateOrder/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDTO> udpateOrder(@PathVariable Long appartmentId, @RequestBody PhotoListSaveRequest photosToReorder){
        log.debug("Updating order of private photos with appartementId {} and values {}", appartmentId, photosToReorder);
        return privatePhotoService.updateOrder(appartmentId, photosToReorder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.debug("Deleting private photo with id {}", id);
        privatePhotoService.delete(id);
    }
}
