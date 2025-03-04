package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.ReservationDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.request.PersonalInformationSaveRequest;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import com.formation.orleanStay.models.request.ReservationSaveRequest;
import com.formation.orleanStay.service.ReservationService;
import com.formation.orleanStay.service.TravellerService;
import com.formation.orleanStay.utils.Findbyid;
import jakarta.websocket.server.PathParam;
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
        log.info("Fetching all reservations");
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO findById(@PathVariable Long id){
        log.info("Fetching reservation with id = {}", id);
        return reservationService.findDTOById(id);
    }

    @GetMapping("/requests/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findAllRequests(){
        log.info("Fetching all reservation requests ");
        return reservationService.findAllReservationRequests();
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findbyUserId(@PathVariable Long userId){
        log.info("Fetching reservations by userId = {} ", userId);
        return reservationService.findbyUserId(userId);
    }

    @GetMapping("/requests/owner/{ownerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findAllRequestsByOwnerId(@PathVariable Long ownerId){
        log.info("Fetching all reservation requests with ownerId = {}", ownerId);
        return reservationService.findReservationRequestsByOwnerId(ownerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@RequestBody ReservationSaveRequest reservationSaveRequest) {
        System.out.println("demande de reservation " + reservationSaveRequest);
        log.info("Add new reservation with value : {}", reservationSaveRequest);
            return reservationService.create(reservationSaveRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO update(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.info("Updating reservation of id {} with value {}", id, reservationSaveRequest);
        PersonalInformation actualPersonalInformation = findbyid.findPersonalInformationById(reservationSaveRequest.getTraveller().getPersonalInformations().getId());
        if(!comparePersonalInformation(reservationSaveRequest.getTraveller().getPersonalInformations(), actualPersonalInformation)){
            travellerService.update(reservationSaveRequest.getTraveller().getId(),reservationSaveRequest.getTraveller());
        }
        return reservationService.update(id, reservationSaveRequest);
    }

    @PutMapping("/{id}/cancelFromTraveller")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO cancelFromTraveller(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.info("Cancelling reservation of id {} with value {}", id, reservationSaveRequest);
        return reservationService.cancelFromTraveller(id, reservationSaveRequest);
    }

    @PutMapping("/{id}/askForDeposit")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO askForDeposit(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.info("Updating reservation of id {} with value {}", id, reservationSaveRequest);
        return reservationService.askForDeposit(id, reservationSaveRequest);
    }

    @PutMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO acceptReservation(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.info("Updating reservation of id {} with value {}", id, reservationSaveRequest);
        return reservationService.acceptReservation(id, reservationSaveRequest);
    }

    @PutMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO rejectReservation(@PathVariable Long id, @RequestBody ReservationSaveRequest reservationSaveRequest) {
        log.info("Updating reservation of id {} with value {}", id, reservationSaveRequest);
        return reservationService.rejectReservation(id, reservationSaveRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Deleting reservation with id {}", id);
        reservationService.delete(id);
    }

    @GetMapping("/withWaitingReservationChat/notFromUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findFilteredReservationsForReservationChatAnswering(@PathVariable Long userId) {
        log.info("Fetching reservation with Reservation chat as last ReservationChat not from utilisateur whith id {}", userId);
        return reservationService.findFilteredReservationsForReservationChatAnswering(userId);
    }

    @GetMapping("/withCheckoutDateLaterThanOneMonthAgo")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> findwithCheckoutDateLaterThanOneMonthAgo() {
        log.info("Fetching reservation with CheckoutDate Later Than One Month Ago");
        return reservationService.findwithCheckoutDateLaterThanOneMonthAgo();
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

    @PostMapping("/findWithCriteria")
    public List<ReservationDTO> findwithCriteria(@RequestBody ReservationResearchRequest reservationResearchRequest) {
        log.info("Fetching reservation with criterias from : {}", reservationResearchRequest);
        return reservationService.findwithCriteria(reservationResearchRequest);
    }

    @GetMapping("/sendInfoTravelEmail/{reservationId}")
    public Long sendInfoTravelEmail(@PathVariable Long reservationId) {
        log.info("Sending info travel email with reservationId : {}", reservationId);
        return reservationService.sendInfoTravelEmail(reservationId);
    }

}
