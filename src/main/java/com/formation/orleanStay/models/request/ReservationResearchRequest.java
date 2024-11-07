package com.formation.orleanStay.models.request;

import lombok.Data;

@Data
public class ReservationResearchRequest {

    private Long ownerId;
    private Long appartmentId;
    private Integer year;
    private Integer month;
    private String state;
    private Boolean notClosed;
    private Long plateformId;
}
