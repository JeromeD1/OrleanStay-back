package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.SignupSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UtilisateurMapper {
    UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur);

    @Mapping(source = "signupSaveRequest.login", target = "login")
    @Mapping(source = "signupSaveRequest.password", target = "password")
    Utilisateur fromSignupSaveRequest(SignupSaveRequest signupSaveRequest);
}
