package com.formation.formationAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // pas de cascade = CascadeType.ALL car personalInformation est aussi lié à la table Utilisateur. Il faut une méthode personnalisée dans le service
    @NotNull
    @JoinColumn(name = "personal_information_id", foreignKey = @ForeignKey(name = "fk_traveller_PersonalInfo"), nullable = false)
    private PersonalInformation personalInformations;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id",  referencedColumnName = "id",  foreignKey = @ForeignKey(name = "fk_traveller_user"), nullable = true)
    @JsonIgnore
    private Utilisateur utilisateur;

    @JsonProperty("utilisateurId")
    public Long getUtilisateurId() {
        return utilisateur != null ? utilisateur.getId() : null;
    }

}
