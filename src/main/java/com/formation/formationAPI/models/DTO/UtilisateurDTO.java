package com.formation.formationAPI.models.DTO;

import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.enumeration.ERole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class UtilisateurDTO {
    private Long id;

    private ERole role;

    private LocalDateTime creationDate;

    private PersonalInformation personalInformations;

}
