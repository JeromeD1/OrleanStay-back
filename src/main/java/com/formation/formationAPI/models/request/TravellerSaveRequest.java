package com.formation.formationAPI.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TravellerSaveRequest {

    private Long id;

    private Long utilisateurId;

    @NotNull
    private PersonalInformationSaveRequest personalInformations;

    private Long personalInformationId;
}
