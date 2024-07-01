package com.formation.formationAPI.models.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationChatDTO {
    private Long id;
    private Long reservationId;
    private UtilisateurDTO utilisateur;
    private String comment;
    private LocalDateTime creationDate;
}
