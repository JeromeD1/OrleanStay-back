package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackSaveRequest {
    private Long id;
    @NotNull
    private Long appartmentId;
    @NotNull
    private Long utilisateurId;
    @NotNull
    private Long reservationId;
    @NotBlank
    private String comment;
}
