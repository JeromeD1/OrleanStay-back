package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.ReservationChatMapper;
import com.formation.orleanStay.mapper.UtilisateurMapper;
import com.formation.orleanStay.models.DTO.ReservationChatDTO;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.ReservationChat;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.ReservationChatSaveRequest;
import com.formation.orleanStay.repository.ReservationChatRepository;
import com.formation.orleanStay.service.ReservationChatService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationChatServiceImpl implements ReservationChatService {
    private final ReservationChatRepository reservationChatRepository;
    private final ReservationChatMapper reservationChatMapper;
    private final Findbyid findbyid;

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
