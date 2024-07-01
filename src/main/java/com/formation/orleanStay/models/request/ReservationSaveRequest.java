package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class ReservationSaveRequest {

    @NotNull
    private Long appartmentId;

    @NotNull
    private TravellerSaveRequest traveller;

    @NotNull
    private LocalDateTime checkinDate;

    @NotNull
    private LocalDateTime checkoutDate;

    @NotNull
    private int nbAdult;

    @NotNull
    private int nbChild;

    @NotNull
    private int nbBaby;

    @NotNull
    private BigDecimal reservationPrice;

    private Boolean accepted;

    private Boolean cancelled;

    private Boolean depositAsked;

    private Boolean depositReceived;

    private String travellerMessage;
}
