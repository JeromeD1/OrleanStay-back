package com.formation.formationAPI.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationChatSaveRequest {
    @NotNull
    private Long utilisateurId;
    @NotNull
    private Long reservationId;
    @NotBlank
    private String comment;
}
