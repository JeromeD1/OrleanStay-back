package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.mapper.ReservationChatMapper;
import com.formation.formationAPI.mapper.UtilisateurMapper;
import com.formation.formationAPI.models.DTO.ReservationChatDTO;
import com.formation.formationAPI.models.entity.Reservation;
import com.formation.formationAPI.models.entity.ReservationChat;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.request.ReservationChatSaveRequest;
import com.formation.formationAPI.repository.ReservationChatRepository;
import com.formation.formationAPI.service.ReservationChatService;
import com.formation.formationAPI.utils.Findbyid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationChatServiceImpl implements ReservationChatService {
    final private ReservationChatRepository reservationChatRepository;
    final private ReservationChatMapper reservationChatMapper;
    final private Findbyid findbyid;
    final private UtilisateurMapper utilisateurMapper;

    public ReservationChatServiceImpl(ReservationChatRepository reservationChatRepository, ReservationChatMapper reservationChatMapper, Findbyid findbyid, UtilisateurMapper utilisateurMapper) {
        this.reservationChatRepository = reservationChatRepository;
        this.reservationChatMapper = reservationChatMapper;
        this.findbyid = findbyid;
        this.utilisateurMapper = utilisateurMapper;
    }

    @Override
    public List<ReservationChatDTO> findByReservationId(Long reservationId) {
        final List<ReservationChat> chatComments = reservationChatRepository.findByReservation_Id(reservationId);
        return chatComments.stream()
                .map(reservationChatMapper::toReservationChatDTO)
                .toList();
    }

    @Override
    public ReservationChatDTO create(ReservationChatSaveRequest reservationChatSaveRequest) {
        //convertion de la requete en entity
        final ReservationChat commentChatToCreate = reservationChatMapper.fromReservationChatSaveRequest(reservationChatSaveRequest);
        //Récupération et ajout de la réservation
        final Reservation reservation = findbyid.findReservationById(reservationChatSaveRequest.getReservationId());
        commentChatToCreate.setReservation(reservation);
        //Récupération et ajout de l'utilisateur
        final Utilisateur utilisateur = findbyid.findUtilisateurById(reservationChatSaveRequest.getUtilisateurId());
        commentChatToCreate.setUtilisateur(utilisateur);

        final ReservationChat savedCommentChat = reservationChatRepository.save(commentChatToCreate);
        return reservationChatMapper.toReservationChatDTO(savedCommentChat);
    }

    @Override
    public void delete(Long id) {
        final ReservationChat commentChatToDelete = findbyid.findReservationChatById(id);
        reservationChatRepository.delete(commentChatToDelete);
    }

}
