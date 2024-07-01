package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.FeedbackDTO;
import com.formation.formationAPI.models.request.FeedbackAnswerSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public interface FeedbackAnswerService {

    @Transactional(propagation = Propagation.REQUIRED)
    FeedbackDTO create(FeedbackAnswerSaveRequest feedbackAnswerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    FeedbackDTO update(Long id, FeedbackAnswerSaveRequest feedbackAnswerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);

}
