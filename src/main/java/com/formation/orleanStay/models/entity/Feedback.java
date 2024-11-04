package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="appartment_id", nullable=false)
    @JsonManagedReference
    @JsonIgnore
    private Appartment appartment;

    @JsonProperty("appartmentId")
    public Long getAppartmentId() {return appartment == null ? null : appartment.getId();}

    @ManyToOne
    @JoinColumn(name="utilisateur_id", nullable=false, foreignKey = @ForeignKey(name = "fk_comment_utilisateur"))
    @JsonManagedReference
    @JsonIgnore
    private Utilisateur utilisateur;

    @JsonProperty("utilisateurId")
    public Long getUtilisateurId() {return utilisateur == null ? null : utilisateur.getId();}

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @JsonProperty("reservationId")
    public Long getReservationId() {return reservation == null ? null : reservation.getId();}

    @Column(nullable = false, length = 2000)
    @NotNull
    private String comment;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime modificationDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    private FeedbackAnswer answer;



    /**
     * Set creation date to now when creating User
     */
    public void setDateForCreation() {
        final LocalDateTime now = LocalDateTime.now();
        this.creationDate = now;
    }

    public void setDateForModification() {
        final LocalDateTime now = LocalDateTime.now();
        this.modificationDate = now;
    }

    /**
     * Auto call of setDateForCreation when creating a new comment
     */
    @PrePersist
    protected void onCreate(){
        setDateForCreation();
        setDateForModification();
    }

    /**
     * Auto call of setDateForModification when updating a comment
     */
    @PreUpdate
    protected void onUpdate(){
        setDateForModification();
    }
}
