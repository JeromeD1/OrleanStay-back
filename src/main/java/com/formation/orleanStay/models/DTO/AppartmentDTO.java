package com.formation.orleanStay.models.DTO;

import com.formation.orleanStay.models.enumeration.EAppartmentType;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class AppartmentDTO {
    private Long id;

    private Long ownerId;

    private String name;

    private String description;

    private String address;

    private String zipcode;

    private String city;

    private String googleMapUrl;

    private String distanceCityCenter;

    private String distanceTrain;

    private String distanceTram;

    private int nightPrice;

    private int caution;

    private int prixPersonneSupplementaire;

    private int menageCourtSejour;

    private int menageLongSejour;

    private int menageLongueDuree;

    private EAppartmentType type;

    private Boolean active;

    private List<InfoDTO> infos;

    private List<PhotoDTO> photos;

    private List<ReservationDTO> reservations;

    private List<FeedbackDTO> comments;

    private DiscountDTO discounts;
}
