package com.formation.orleanStay.models.DTO;

import com.formation.orleanStay.models.entity.Traveller;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private Long id;

    private Long appartmentId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private int nbAdult;

    private int nbChild;

    private int nbBaby;

    private BigDecimal reservationPrice;

    private Boolean accepted;

    private Boolean cancelled;

    private Boolean depositAsked;

    private Boolean depositReceived;

    private Traveller traveller;

    private String travellerMessage;
}
