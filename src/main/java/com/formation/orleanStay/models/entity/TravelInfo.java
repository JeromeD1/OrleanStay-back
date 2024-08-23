package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.formation.orleanStay.models.enumeration.ETravelInfoContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private ETravelInfoContentType contentType;

    @Column(nullable = false)
    private Integer positionOrder;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    @JoinColumn(name = "appartment_id", foreignKey = @ForeignKey(name = "fk_appartment_travelInfo"), nullable = true)
    private Appartment appartment;

    // Ajout d'une méthode pour sérialiser uniquement l'ID de l'appartement
    @JsonProperty("appartmentId")
    public Long getAppartmentId() {
        return appartment != null ? appartment.getId() : null;
    }
}
