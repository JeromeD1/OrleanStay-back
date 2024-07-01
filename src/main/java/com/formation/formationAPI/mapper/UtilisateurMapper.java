package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import com.formation.formationAPI.models.entity.Utilisateur;
import com.formation.formationAPI.models.request.SignupSaveRequest;
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
