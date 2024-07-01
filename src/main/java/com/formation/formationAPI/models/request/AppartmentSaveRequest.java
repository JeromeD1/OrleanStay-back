package com.formation.formationAPI.models.request;

import com.formation.formationAPI.models.entity.Discount;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.enumeration.EAppartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class AppartmentSaveRequest {

    private Long id;

    @NotBlank
    private Long ownerId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String address;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String city;

    @NotBlank
    private String googleMapUrl;

    @NotBlank
    private String distanceCityCenter;

    @NotBlank
    private String distanceTrain;

    @NotBlank
    private String distanceTram;

    @NotBlank
    private int nightPrice;

    @NotBlank
    private int caution;

    @NotBlank
    private int menageCourtSejour;

    @NotBlank
    private int menageLongSejour;

    @NotBlank
    private int menageLongueDuree;

    @NotBlank
    private EAppartmentType type;

    private Boolean active;

    @NotBlank
    private Long discountId;

    private Discount discounts;

    private Utilisateur owner;
}
