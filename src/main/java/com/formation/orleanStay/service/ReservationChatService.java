package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.ReservationChatDTO;
import com.formation.orleanStay.models.request.ReservationChatSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface ReservationChatService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationChatDTO> findByReservationId(Long reservationId);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationChatDTO create(ReservationChatSaveRequest reservationChatSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
