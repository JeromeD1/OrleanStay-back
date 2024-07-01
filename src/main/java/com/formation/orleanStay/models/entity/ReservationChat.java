package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation_chat")
public class ReservationChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", foreignKey = @ForeignKey(name = "fk_utilisateur_reservationChat"))
    @JsonIgnore
    private Utilisateur utilisateur;

    @JsonProperty("utilisateurId")
    public Long getUtilisateurId(){return utilisateur == null ? null : utilisateur.getId();}

    @ManyToOne
    @NotNull
    @JoinColumn(name = "reservation_id", foreignKey = @ForeignKey(name = "fk_reservation_reservationChat"))
    @JsonIgnore
    private Reservation reservation;

    @JsonProperty("reservationId")
    public Long getReservationId(){return reservation == null ? null : reservation.getId();}

    @Column(nullable = false)
    @NotNull
    private LocalDateTime creationDate;

    /**
     * Set creation date to now when creating User
     */
    public void setDateForCreation() {
        final LocalDateTime now = LocalDateTime.now();
        this.creationDate = now;
    }

    /**
     * Auto call of setDateForCreation when creating a new comment
     */
    @PrePersist
    protected void onCreate(){
        setDateForCreation();
    }
}
