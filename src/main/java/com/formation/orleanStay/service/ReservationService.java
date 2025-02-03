package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.ReservationDTO;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import com.formation.orleanStay.models.request.ReservationSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface ReservationService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    ReservationDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findAllReservationRequests();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findbyUserId(Long userId);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findReservationRequestsByOwnerId(Long ownerId);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO create(ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO update(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO cancelFromTraveller(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO askForDeposit(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO rejectReservation(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO acceptReservation(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findFilteredReservationsForReservationChatAnswering(Long userId);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findwithCheckoutDateLaterThanOneMonthAgo();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findwithCriteria(ReservationResearchRequest reservationResearchRequest);

    Long sendInfoTravelEmail(Long reservationId);
}
