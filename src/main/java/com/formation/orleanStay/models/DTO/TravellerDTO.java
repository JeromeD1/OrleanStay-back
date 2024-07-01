package com.formation.orleanStay.models.DTO;

import com.formation.orleanStay.models.entity.PersonalInformation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TravellerDTO {

    private Long id;

    private Long utilisateurId;

    private PersonalInformation personalInformations;
}
