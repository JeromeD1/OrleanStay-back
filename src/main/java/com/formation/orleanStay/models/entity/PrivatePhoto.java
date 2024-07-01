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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appartment_private_photo")
public class PrivatePhoto {
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
    @JoinColumn(name = "appartment_id", foreignKey = @ForeignKey(name = "fk_appartment_private_photo"), nullable = true)
    private Appartment appartment;

    // Ajout d'une méthode pour sérialiser uniquement l'ID de l'appartement
    @JsonProperty("appartmentId")
    public Long getAppartmentId() {
        return appartment != null ? appartment.getId() : null;
    }

}
