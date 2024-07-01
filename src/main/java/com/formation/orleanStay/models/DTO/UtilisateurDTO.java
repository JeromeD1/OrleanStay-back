package com.formation.orleanStay.models.DTO;

import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.enumeration.ERole;
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
