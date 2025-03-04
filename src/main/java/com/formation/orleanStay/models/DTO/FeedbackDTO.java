package com.formation.orleanStay.models.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private Long id;
    private Long appartmentId;
    private Long utilisateurId;
    private Long reservationId;
    private String comment;
    private FeedbackAnswerDTO answer;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
