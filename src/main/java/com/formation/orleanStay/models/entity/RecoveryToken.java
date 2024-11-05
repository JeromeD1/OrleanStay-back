package com.formation.orleanStay.models.entity;

import com.formation.orleanStay.models.request.RecoveryTokenRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @NotNull
    @JoinColumn(name = "utilisateur_id", nullable = false, foreignKey = @ForeignKey(name = "fk_token_utilisateur"))
    private Utilisateur utilisateur;

    public RecoveryToken(RecoveryTokenRequest recoveryTokenRequest) {
        this.token = recoveryTokenRequest.getToken();
        this.utilisateur = recoveryTokenRequest.getUtilisateur();
    }
}
