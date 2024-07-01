package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.PersonalInformationDTO;
import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.models.request.SignupSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonalInformationMapper {
    /**
     * Mapping personalInformationId to the id of {@link PersonalInformation} entity
     *
     * @param personalInformationId to map
     * @return the {@link PersonalInformation} with the id of the personalInformationId value
     */
    default PersonalInformation fromPersonalInformationId(Long personalInformationId) {
        if(personalInformationId == null) {
            return null;
        }

        final PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setId(personalInformationId);
        return personalInformation;

    }


    /**
     * Mapping {@link PersonalInformation} to {@link PersonalInformationDTO}
     *
     * @param personalInformation to map
     * @return the {@link PersonalInformationDTO} mapped from {@link PersonalInformation}
     */
    PersonalInformationDTO toPersonalInformationDTO(PersonalInformation personalInformation);



    /**
     * Mapping {@link PersonalInformationSaveRequest} to {@link PersonalInformation}
     *
     * @param personalInformationSaveRequest to map
     * @return the {@link PersonalInformation} mapped from {@link PersonalInformationSaveRequest}
     */
    PersonalInformation fromPersonalInformationSaveRequest(PersonalInformationSaveRequest personalInformationSaveRequest);


    /**
     * Override {@link PersonalInformation} with {@link PersonalInformationSaveRequest}
     *
     * @param saveRequest the {@link PersonalInformationSaveRequest} used to override {@link PersonalInformation}
     * @param personalInformation {@link PersonalInformation} to be overwritten
     */
    @Mapping(source = "saveRequest.firstname", target = "firstname")
    @Mapping(source = "saveRequest.lastname", target = "lastname")
    @Mapping(source = "saveRequest.email", target = "email")
    @Mapping(source = "saveRequest.phone", target = "phone")
    @Mapping(source = "saveRequest.address", target = "address")
    @Mapping(source = "saveRequest.city", target = "city")
    @Mapping(source = "saveRequest.country", target = "country")
    void overrideFromPersonalInformationSaveRequest(PersonalInformationSaveRequest saveRequest, @MappingTarget PersonalInformation personalInformation);

    /**
     * Mapping {@link SignupSaveRequest} to {@link PersonalInformation}
     *
     * @param saveRequest to map
     * @return the {@link PersonalInformation} mapped from {@link SignupSaveRequest}
     */
    @Mapping(source = "saveRequest.firstname", target = "firstname")
    @Mapping(source = "saveRequest.lastname", target = "lastname")
    @Mapping(source = "saveRequest.email", target = "email")
    @Mapping(source = "saveRequest.phone", target = "phone")
    @Mapping(source = "saveRequest.address", target = "address")
    @Mapping(source = "saveRequest.city", target = "city")
    @Mapping(source = "saveRequest.country", target = "country")
    PersonalInformation fromSignupSaveRequest(SignupSaveRequest saveRequest);
}
