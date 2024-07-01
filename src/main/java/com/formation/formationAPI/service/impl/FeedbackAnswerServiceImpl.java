package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.mapper.FeedbackAnswerMapper;
import com.formation.formationAPI.mapper.FeedbackMapper;
import com.formation.formationAPI.models.DTO.FeedbackAnswerDTO;
import com.formation.formationAPI.models.DTO.FeedbackDTO;
import com.formation.formationAPI.models.entity.Feedback;
import com.formation.formationAPI.models.entity.FeedbackAnswer;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.request.FeedbackAnswerSaveRequest;
import com.formation.formationAPI.repository.FeedbackAnswerRepository;
import com.formation.formationAPI.repository.FeedbackRepository;
import com.formation.formationAPI.service.FeedbackAnswerService;
import com.formation.formationAPI.utils.Findbyid;
import org.springframework.stereotype.Service;

@Service
public class FeedbackAnswerServiceImpl implements FeedbackAnswerService {

    private final FeedbackAnswerRepository feedbackAnswerRepository;
    private final FeedbackAnswerMapper feedbackAnswerMapper;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final Findbyid findbyid;

    public FeedbackAnswerServiceImpl(FeedbackAnswerRepository feedbackAnswerRepository, FeedbackAnswerMapper feedbackAnswerMapper, FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper, Findbyid findbyid) {
        this.feedbackAnswerRepository = feedbackAnswerRepository;
        this.feedbackAnswerMapper = feedbackAnswerMapper;
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
        this.findbyid = findbyid;
    }


    @Override
    public FeedbackDTO create(FeedbackAnswerSaveRequest feedbackAnswerSaveRequest) {
        //Récupération du feedback à mettre à jour
        final Feedback feedbackToUpdate = findbyid.findFeedbackById(feedbackAnswerSaveRequest.getCommentId());
        //Conversion de la requète en objet FeedbackAnswer
        final FeedbackAnswer feedbackAnswerToCreate = feedbackAnswerMapper.fromFeedbackAnswerSaveRequest(feedbackAnswerSaveRequest);
        //ajout de l'objet Feedback à l'answer
        feedbackAnswerToCreate.setComment(feedbackToUpdate);
        //Récupération et ajout de l'utilisateur à l'answer
        final Utilisateur utilisateurOfAnswer = findbyid.findUtilisateurById(feedbackAnswerSaveRequest.getUtilisateurId());
        feedbackAnswerToCreate.setUtilisateur(utilisateurOfAnswer);

        //Sauvegarde de l'answer en bdd
        final FeedbackAnswer savedFeedbackAnswer = feedbackAnswerRepository.save(feedbackAnswerToCreate);

        //Ajout de l'answer sauvegardée au FeedbackToUpdate
        feedbackToUpdate.setAnswer(savedFeedbackAnswer);
        //Sauvegarde de du feedback mis à jour
        feedbackRepository.save(feedbackToUpdate);


        FeedbackAnswerDTO savedFeedbackAnswerDTO = feedbackAnswerMapper.toFeedbackAnswerDTO(savedFeedbackAnswer);
        FeedbackDTO feedbackDTOToReturn = feedbackMapper.toFeedbackDTO(feedbackToUpdate);
        feedbackDTOToReturn.setAnswer(savedFeedbackAnswerDTO);
        return feedbackDTOToReturn;
    }

    @Override
    public FeedbackDTO update(Long id, FeedbackAnswerSaveRequest feedbackAnswerSaveRequest) {
        //Récupération de la feedbackAnswer suivant id
        final FeedbackAnswer feedbackAnswerToUpdate = findbyid.findFeedbackAnswerById(id);
        //Mise à jour des données
        feedbackAnswerMapper.overrideFromFeedbackAnswerSaveRequest(feedbackAnswerSaveRequest, feedbackAnswerToUpdate);
        //Sauvegarde en BDD
        FeedbackAnswer savedFeedbackAnswer = feedbackAnswerRepository.save(feedbackAnswerToUpdate);
        //mise à jour de modificationDate pour le renvoi
        savedFeedbackAnswer.setDateForModification();
        //Conversion de l'answer en DTO
        FeedbackAnswerDTO savedFeedbackAnswerDTO = feedbackAnswerMapper.toFeedbackAnswerDTO(savedFeedbackAnswer);
        //Récupération du commentaire associé à l'answer
        final Feedback feedbackToReturn = findbyid.findFeedbackById(feedbackAnswerSaveRequest.getCommentId());
        //Conversion du commentaire en DTO
        final FeedbackDTO feedbackToReturnDTO = feedbackMapper.toFeedbackDTO(feedbackToReturn);
        //Ajout de la réponse au commentaire
        feedbackToReturnDTO.setAnswer(savedFeedbackAnswerDTO);
        //Envoi des données
        return feedbackToReturnDTO;

    }

    @Override
    public void delete(Long id) {
        //Récupération du feedbackAnswer
        final FeedbackAnswer feedbackAnswerToDelete = findbyid.findFeedbackAnswerById(id);
        //Récupération du commentaire d'origine
        final Feedback feedbackToUpdate = findbyid.findFeedbackById(feedbackAnswerToDelete.getCommentId());
        //Suppression de l'answer du commentaire d'origine
        feedbackToUpdate.setAnswer(null);
        feedbackRepository.save(feedbackToUpdate);
        //suppression de l'answer
        feedbackAnswerRepository.delete(feedbackAnswerToDelete);
    }
}
