package com.formation.formationAPI.models.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackAnswerDTO {
    private Long id;
    private Long commentId;
    private Long utilisateurId;
    private String commentAnswer;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
