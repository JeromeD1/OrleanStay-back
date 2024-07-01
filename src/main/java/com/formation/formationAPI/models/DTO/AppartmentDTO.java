package com.formation.formationAPI.models.DTO;

import com.formation.formationAPI.models.entity.Appartment;
import com.formation.formationAPI.models.entity.Discount;
import com.formation.formationAPI.models.entity.Info;
import com.formation.formationAPI.models.entity.Photo;
import com.formation.formationAPI.models.enumeration.EAppartmentType;
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
