package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.InfoDTO;
import com.formation.orleanStay.models.request.InfoListSaveRequest;
import com.formation.orleanStay.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/info")
public class InfoController {

    final private InfoService infoService;

    public InfoController(InfoService infoService){
        this.infoService = infoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InfoDTO> findAll(){
        log.info("Fetching all info of all appartments");
        return infoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InfoDTO findById(@PathVariable Long id){
        log.info("Fetching info with id = {}", id);
        return infoService.findDTOById(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public InfoDTO create(@RequestBody InfoSaveRequest infoSaveRequest) {
//        log.info("Add new info with value : {}", infoSaveRequest);
//        return infoService.create(infoSaveRequest);
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public InfoDTO update(@PathVariable Long id, @RequestBody InfoSaveRequest infoSaveRequest) {
//        log.info("Updating info of if {} with value {}", id, infoSaveRequest);
//        return infoService.update(id, infoSaveRequest);
//    }

    @PutMapping("/{appartmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InfoDTO> update(@PathVariable Long appartmentId, @RequestBody InfoListSaveRequest infoSaveRequest) {
        log.info("Updating appartment infos of appartmentId {} with value {}", appartmentId, infoSaveRequest);
        return infoService.update(appartmentId, infoSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Deleting info with id {}", id);
        infoService.delete(id);
    }
}
