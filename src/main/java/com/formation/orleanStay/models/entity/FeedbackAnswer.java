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
public class FeedbackAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="comment_id", nullable=false, foreignKey = @ForeignKey(name = "fk_comment_commentAnswer"))
    @JsonManagedReference
    @JsonIgnore
    private Feedback comment;

    @JsonProperty("commentId")
    public Long getCommentId() {return comment == null ? null : comment.getId();}

    @ManyToOne
    @JoinColumn(name="utilisateur_id", nullable=false, foreignKey = @ForeignKey(name = "fk_commentAnswer_utilisateur"))
    @JsonManagedReference
    @JsonIgnore
    private Utilisateur utilisateur;

    @JsonProperty("utilisateurId")
    public Long getUtilisateurId() {return utilisateur == null ? null : utilisateur.getId();}

    @Column(nullable = false)
    @NotNull
    private String commentAnswer;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime modificationDate;



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
