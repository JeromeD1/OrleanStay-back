package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.FeedbackDTO;
import com.formation.orleanStay.models.request.FeedbackSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface FeedbackService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<FeedbackDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    FeedbackDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<FeedbackDTO> getByUserIdAndAppartmentId(Long userId, Long appartmentId);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<FeedbackDTO> findFeedbackByReservationId(Long reservationId);

    @Transactional(propagation = Propagation.REQUIRED)
    FeedbackDTO create(FeedbackSaveRequest feedbackSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    FeedbackDTO update(Long id, FeedbackSaveRequest feedbackSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
