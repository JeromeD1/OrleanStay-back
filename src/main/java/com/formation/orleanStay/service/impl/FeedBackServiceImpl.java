package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.FeedbackMapper;
import com.formation.orleanStay.models.DTO.FeedbackDTO;
import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.entity.Feedback;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.FeedbackSaveRequest;
import com.formation.orleanStay.repository.FeedbackRepository;
import com.formation.orleanStay.service.FeedbackAnswerService;
import com.formation.orleanStay.service.FeedbackService;
import com.formation.orleanStay.utils.Findbyid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final Findbyid findbyid;
    private final FeedbackAnswerService feedbackAnswerService;

    public FeedBackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper, Findbyid findbyid, FeedbackAnswerService feedbackAnswerService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
        this.findbyid = findbyid;
        this.feedbackAnswerService = feedbackAnswerService;
    }

    @Override
    public List<FeedbackDTO> findAll() {
        final List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .map(feedbackMapper::toFeedbackDTO)
                .toList();
    }

    @Override
    public FeedbackDTO findDTOById(Long id) {
        final Feedback comment = findbyid.findFeedbackById(id);
        return feedbackMapper.toFeedbackDTO(comment);
    }

    @Override
    public List<FeedbackDTO> getByUserIdAndAppartmentId(Long userId, Long appartmentId) {
        final List<Feedback> feedbacks = feedbackRepository.findFeedbackByUtilisateurIdAndAppartmentId(userId, appartmentId);
        return feedbacks.stream()
                .map(feedbackMapper::toFeedbackDTO)
                .toList();
    }

    @Override
    public List<FeedbackDTO> findFeedbackByReservationId(Long reservationId) {
            final List<Feedback> feedbacks = feedbackRepository.findFeedbackByReservationId(reservationId);
            return feedbacks.stream()
                    .map(feedbackMapper::toFeedbackDTO)
                    .toList();
    }

    @Override
    public FeedbackDTO create(FeedbackSaveRequest feedbackSaveRequest) {
        final Feedback feedbackToSave = feedbackMapper.fromFeedbackSaveRequest(feedbackSaveRequest);
        //récupération et ajout de l'appartement
        final Appartment appartment = findbyid.findAppartmentById(feedbackSaveRequest.getAppartmentId());
        feedbackToSave.setAppartment(appartment);
        //récupération et ajout de l'utilisateur
        final Utilisateur utilisateur = findbyid.findUtilisateurById(feedbackSaveRequest.getUtilisateurId());
        feedbackToSave.setUtilisateur(utilisateur);

        final Reservation reservation = findbyid.findReservationById(feedbackSaveRequest.getReservationId());
        feedbackToSave.setReservation(reservation);

        final Feedback savedFeedback = feedbackRepository.save(feedbackToSave);
        return feedbackMapper.toFeedbackDTO(savedFeedback);
    }

    @Override
    public  FeedbackDTO update(Long id, FeedbackSaveRequest feedbackSaveRequest) {
        if(feedbackSaveRequest.getId() == null) {
            feedbackSaveRequest.setId(id);
        }

        final Feedback feedbackToUpdate = findbyid.findFeedbackById(id);
        feedbackMapper.overrideFromFeedbackSaveRequest(feedbackSaveRequest, feedbackToUpdate);
        final Feedback savedFeedback = feedbackRepository.save(feedbackToUpdate);
        //mise à jour de la date de modification dans le feedback retourné
        savedFeedback.setDateForModification();
        return feedbackMapper.toFeedbackDTO(savedFeedback);
    }


    @Override
    public void delete(Long id) {
        final Feedback comment = findbyid.findFeedbackById(id);
        if(comment.getAnswer() != null) {
            feedbackAnswerService.delete(comment.getAnswer().getId());
        }
        feedbackRepository.delete(comment);
    }

}
