package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.DTO.InfoDTO;
import com.formation.formationAPI.models.DTO.PersonalInformationDTO;
import com.formation.formationAPI.models.request.InfoSaveRequest;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.service.PersonalInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/personalInformation")
public class PersonalInformationController {
    final private PersonalInformationService personalInformationService;

    public PersonalInformationController(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonalInformationDTO> findAll(){
        log.debug("Fetching all personalInformation");
        return personalInformationService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonalInformationDTO findById(@PathVariable Long id){
        log.debug("Fetching personalInformation with id = {}", id);
        return personalInformationService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalInformationDTO create(@RequestBody PersonalInformationSaveRequest personalInformationSaveRequest) {
        log.debug("Add new personalInformation with value : {}", personalInformationSaveRequest);
        return personalInformationService.create(personalInformationSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonalInformationDTO update(@PathVariable Long id, @RequestBody PersonalInformationSaveRequest personalInformationSaveRequest) {
        log.debug("Updating personalInformation of if {} with value {}", id, personalInformationSaveRequest);
        return personalInformationService.update(id, personalInformationSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.debug("Deleting personalInformation with id {}", id);
        personalInformationService.delete(id);
    }
}
