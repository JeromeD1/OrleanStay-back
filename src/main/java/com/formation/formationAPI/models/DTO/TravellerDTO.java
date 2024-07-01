package com.formation.formationAPI.models.DTO;

import com.formation.formationAPI.models.entity.PersonalInformation;
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
