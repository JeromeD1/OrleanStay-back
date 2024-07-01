package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Appartment_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private Integer positionOrder;

    @Column(nullable = false)
    @NotNull
    private String info;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore // permet d'éviter de renvoyer l'objet appartment dans le findAll de l'objet appartment
    @JoinColumn(name = "appartment_id", foreignKey = @ForeignKey(name = "fk_appartment_info"), nullable = true)
    private Appartment appartment;

    // Ajout d'une méthode pour sérialiser uniquement l'ID de l'appartement
    @JsonProperty("appartmentId")
    public Long getAppartmentId() {
        return appartment != null ? appartment.getId() : null;
    }
}
