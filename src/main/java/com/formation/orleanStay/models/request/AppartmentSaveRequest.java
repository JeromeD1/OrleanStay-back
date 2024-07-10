package com.formation.orleanStay.models.request;

import com.formation.orleanStay.models.entity.Discount;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.enumeration.EAppartmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppartmentSaveRequest {

    private Long id;

    @NotNull
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

    @NotNull
    private int nightPrice;

    @NotNull
    private int caution;

    @NotNull
    private int menageCourtSejour;

    @NotNull
    private int menageLongSejour;

    @NotNull
    private int menageLongueDuree;

    @NotNull
    private EAppartmentType type;

    private Boolean active;

    @NotNull
    private Long discountId;

    private Discount discounts;

    private Utilisateur owner;
}
