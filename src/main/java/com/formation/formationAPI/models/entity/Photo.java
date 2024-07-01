package com.formation.formationAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Appartment_photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "imgUrl")
    @NotNull
    private String imgUrl;

    @Column
    @NotNull
    private Integer positionOrder;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    @JoinColumn(name = "appartment_id", foreignKey = @ForeignKey(name = "fk_appartment_photo"), nullable = true)
    private Appartment appartment;

    // Ajout d'une méthode pour sérialiser uniquement l'ID de l'appartement
    @JsonProperty("appartmentId")
    public Long getAppartmentId() {
        return appartment != null ? appartment.getId() : null;
    }

}
