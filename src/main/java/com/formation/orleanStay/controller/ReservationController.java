package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.ReservationDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.request.PersonalInformationSaveRequest;
import com.formation.orleanStay.models.request.ReservationSaveRequest;
import com.formation.orleanStay.service.ReservationService;
import com.formation.orleanStay.service.TravellerService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("reservation")
public class ReservationController {

    final private ReservationService reservationService;
    final private TravellerService travellerService;
    final private Findbyid findbyid;

    public ReservationController(ReservationService reservationService, TravellerService travellerService, Findbyid findbyid) {
        this.reservationService = reservationService;
        this.travellerService = travellerService;
        this.findbyid = findbyid;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findAll(){
        log.debug("Fetching all reservations");
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO findById(@PathVariable Long id){
        log.debug("Fetching reservation with id = {}", id);
        return reservationService.findDTOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@RequestBody ReservationSaveRequest reservationSaveRequest) {
        System.out.println("demande de reservation " + reservationSaveRequest);
        log.debug("Add new reservation with value : {}", reservationSaveRequest);
            return reservationService.create(reservationSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO update(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.debug("Updating reservation of if {} with value {}", id, reservationSaveRequest);
        PersonalInformation actualPersonalInformation = findbyid.findPersonalInformationById(reservationSaveRequest.getTraveller().getPersonalInformations().getId());
        if(!comparePersonalInformation(reservationSaveRequest.getTraveller().getPersonalInformations(), actualPersonalInformation)){
            travellerService.update(reservationSaveRequest.getTraveller().getId(),reservationSaveRequest.getTraveller());
        }
        return reservationService.update(id, reservationSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.debug("Deleting reservation with id {}", id);
        reservationService.delete(id);
    }


    private Boolean comparePersonalInformation(PersonalInformationSaveRequest request, PersonalInformation information){
        if (request == null || information == null) {
            return false;
        }
        return Objects.equals(request.getFirstname(), information.getFirstname()) &&
                Objects.equals(request.getLastname(), information.getLastname()) &&
                Objects.equals(request.getEmail(), information.getEmail()) &&
                Objects.equals(request.getPhone(), information.getPhone()) &&
                Objects.equals(request.getAddress(), information.getAddress()) &&
                Objects.equals(request.getCity(), information.getCity()) &&
                Objects.equals(request.getCountry(), information.getCountry());
    }

}
