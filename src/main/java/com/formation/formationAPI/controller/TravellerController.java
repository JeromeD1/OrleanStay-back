package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.DTO.TravellerDTO;
import com.formation.formationAPI.models.request.TravellerSaveRequest;
import com.formation.formationAPI.service.TravellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/traveller")
public class TravellerController {

    final private TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TravellerDTO> findAll(){
        log.debug("Fetching all traveller");
        return travellerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TravellerDTO findById(@PathVariable Long id){
        log.debug("Fetching traveller with id = {}", id);
        return travellerService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravellerDTO create(@RequestBody TravellerSaveRequest travellerSaveRequest) {
        log.debug("Add new traveller with value : {}", travellerSaveRequest);
        if(travellerSaveRequest.getUtilisateurId() == null){
            return travellerService.createWithoutUtilisateur(travellerSaveRequest);
        } else {
            return travellerService.createWithUtilisateur(travellerSaveRequest);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TravellerDTO update(@PathVariable Long id, @RequestBody TravellerSaveRequest travellerSaveRequest) {
        log.debug("Updating traveller of if {} with value {}", id, travellerSaveRequest);
        return travellerService.update(id, travellerSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.debug("Deleting traveller with id {}", id);
        travellerService.delete(id);
    }
}
