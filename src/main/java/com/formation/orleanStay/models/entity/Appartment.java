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

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private String address;

    @Column(length = 10)
    @Size(max = 10)
    @NotNull
    private String zipcode;

    @Column(length = 100)
    @Size(max = 100)
    @NotNull
    private String city;

    @Column(length = 5000)
    @Size(max = 5000)
    @NotNull
    private String googleMapUrl;

    @Column(name = "distanceCityCenter")
    @NotNull
    private String distanceCityCenter;

    @Column(name = "distanceTrain")
    @NotNull
    private String distanceTrain;

    @Column(name = "distanceTram")
    @NotNull
    private String distanceTram;

    @Column(name = "nightPrice")
    @NotNull
    private int nightPrice;

    @Column
    @NotNull
    private int caution;

    @Column
    @NotNull
    private int menageCourtSejour;

    @Column
    @NotNull
    private int menageLongSejour;

    @Column
    @NotNull
    private int menageLongueDuree;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    @NotNull
    private EAppartmentType type;

    @Column(name = "isActive", columnDefinition = "boolean default true")
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
    @JoinColumn(name = "discount_id", foreignKey = @ForeignKey(name = "fk_appartment_discount"))
    private Discount discounts;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id",  referencedColumnName = "id",  foreignKey = @ForeignKey(name = "fk_appartment_user"))
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
