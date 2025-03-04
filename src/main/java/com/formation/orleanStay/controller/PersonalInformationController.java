package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.PersonalInformationDTO;
import com.formation.orleanStay.models.request.PersonalInformationSaveRequest;
import com.formation.orleanStay.service.PersonalInformationService;
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
        log.info("Fetching all personalInformation");
        return personalInformationService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonalInformationDTO findById(@PathVariable Long id){
        log.info("Fetching personalInformation with id = {}", id);
        return personalInformationService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalInformationDTO create(@RequestBody PersonalInformationSaveRequest personalInformationSaveRequest) {
        log.info("Add new personalInformation with value : {}", personalInformationSaveRequest);
        return personalInformationService.create(personalInformationSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonalInformationDTO update(@PathVariable Long id, @RequestBody PersonalInformationSaveRequest personalInformationSaveRequest) {
        log.info("Updating personalInformation of if {} with value {}", id, personalInformationSaveRequest);
        return personalInformationService.update(id, personalInformationSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Deleting personalInformation with id {}", id);
        personalInformationService.delete(id);
    }
}
