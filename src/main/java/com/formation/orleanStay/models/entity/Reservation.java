package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    @NotNull
    @JoinColumn(name = "appartment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appartment_reservation"))
    private Appartment appartment;

    @JsonProperty("appartmentId")
    public Long getAppartmentId(){return appartment != null ? appartment.getId() : null;}

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotNull
    @JoinColumn(name = "traveller_id", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_traveller"))
    private Traveller traveller;

//    @JsonProperty("travellerId")
//    public Long getTravellerId(){return  traveller != null ? traveller.getId() : null;}

    @Column(nullable = false)
    @NotNull
    private LocalDateTime checkinDate;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime checkoutDate;

    @Column(nullable = false)
    @NotNull
    private int nbAdult;

    @Column(nullable = false, columnDefinition = "int default 0")
    @NotNull
    private int nbChild = 0;

    @Column(nullable = false, columnDefinition = "int default 0")
    @NotNull
    private int nbBaby = 0;

    @Column(nullable = false, columnDefinition = "Decimal(10,2)")
    @NotNull
    private BigDecimal reservationPrice;

    @Column(name = "is_accepted", columnDefinition = "boolean default false", nullable = false)
    @NotNull
    @JsonProperty("isAccepted")
    private Boolean accepted = false;

    @Column(name = "is_cancelled" , columnDefinition = "boolean default false", nullable = false)
    @NotNull
    @JsonProperty("isCancelled")
    private Boolean cancelled = false;

    @Column(name = "is_deposit_asked" , columnDefinition = "boolean default false", nullable = false)
    @NotNull
    @JsonProperty("isDepositAsked")
    private Boolean depositAsked = false;

    @Column(name = "is_deposit_received" , columnDefinition = "boolean default false", nullable = false)
    @NotNull
    @JsonProperty("isDepositReceived")
    private Boolean depositReceived = false;

    @Column
    private Integer depositValue;

    @Column(columnDefinition = "text")
    private String travellerMessage;

    public Long getNumberOfNights() {
        return Duration.between(checkinDate, checkoutDate).toDays();
    }


    @PrePersist
    void prePersist() {
        if (this.accepted == null) {
            this.accepted = false;
        }
        if (this.cancelled == null) {
            this.cancelled = false;
        }
        if (this.depositAsked == null) {
            this.depositAsked = false;
        }
        if (this.depositReceived == null) {
            this.depositReceived = false;
        }

        if (this.depositValue == null) {
            this.depositValue = (int) Math.floor( (0.3 * this.reservationPrice.intValue())) ;
        }
    }


}
