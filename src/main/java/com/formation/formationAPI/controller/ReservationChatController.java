package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.DTO.ReservationChatDTO;
import com.formation.formationAPI.models.request.ReservationChatSaveRequest;
import com.formation.formationAPI.service.ReservationChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reservationChat")
public class ReservationChatController {
    final private ReservationChatService reservationChatService;

    public ReservationChatController(ReservationChatService reservationChatService) {
        this.reservationChatService = reservationChatService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationChatDTO> findByReservationId(@PathVariable Long id) {
        log.debug("Fetching chat comments with reservationId = {}", id);
        return reservationChatService.findByReservationId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationChatDTO create(@RequestBody ReservationChatSaveRequest reservationChatSaveRequest) {
        log.debug("Creating new chat comment from reservationChatSaveRequest :  {}", reservationChatSaveRequest);
        return reservationChatService.create(reservationChatSaveRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.debug("Deleting chat comment of id = {}", id);
        reservationChatService.delete(id);
    }
}
