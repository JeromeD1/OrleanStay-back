package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.formation.orleanStay.models.enumeration.EAppartmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(nullable = false)
    @NotNull
    private String address;

    @Column(length = 10, nullable = false)
    @Size(max = 10)
    @NotNull
    private String zipcode;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotNull
    private String city;

    @Column(length = 5000, nullable = false)
    @Size(max = 5000)
    @NotNull
    private String googleMapUrl;

    @Column(name = "distanceCityCenter", nullable = false)
    @NotNull
    private String distanceCityCenter;

    @Column(name = "distanceTrain", nullable = false)
    @NotNull
    private String distanceTrain;

    @Column(name = "distanceTram", nullable = false)
    @NotNull
    private String distanceTram;

    @Column(name = "nightPrice", nullable = false)
    @NotNull
    private int nightPrice;

    @Column(nullable = false)
    @NotNull
    private int caution;

    @Column(nullable = false)
    @NotNull
    private int prixPersonneSupplementaire;

    @Column(nullable = false)
    @NotNull
    private int menageCourtSejour;

    @Column(nullable = false)
    @NotNull
    private int menageLongSejour;

    @Column(nullable = false)
    @NotNull
    private int menageLongueDuree;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    @NotNull
    private EAppartmentType type;

    @Column(name = "isActive", columnDefinition = "boolean default true", nullable = false)
    @JsonProperty("isActive")
    private Boolean active = true;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "appartment")
    @JsonBackReference
    private List<Info> infos;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "appartment")
    @JsonBackReference
    private List<Photo> photos;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "appartment")
    @JsonBackReference
    private List<Reservation> reservations;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "discount_id", foreignKey = @ForeignKey(name = "fk_appartment_discount"), nullable = false)
    private Discount discounts;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id",  referencedColumnName = "id",  foreignKey = @ForeignKey(name = "fk_appartment_user"), nullable = false)
    @JsonIgnore
    private Utilisateur owner;

    @JsonProperty("ownerId")
    public Long getOwnerId() {
        return owner != null ? owner.getId() : null;
    }

    @OneToMany(mappedBy="appartment", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Feedback> comments;

    @PrePersist
    void prePersist() {
        if (this.active == null) {
            this.active = true;
        }
    }

    public void addPhoto(Photo photoToAdd){
        photos.add(photoToAdd);
    }
}
