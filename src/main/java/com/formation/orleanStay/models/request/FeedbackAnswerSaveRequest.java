package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackAnswerSaveRequest {
    private Long id;
    @NotNull
    private Long commentId;
    @NotNull
    private Long utilisateurId;
    @NotBlank
    private String commentAnswer;
}
