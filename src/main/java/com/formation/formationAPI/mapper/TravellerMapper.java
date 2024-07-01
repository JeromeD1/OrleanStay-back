package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.PersonalInformationDTO;
import com.formation.formationAPI.models.DTO.TravellerDTO;
import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.entity.Traveller;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.models.request.TravellerSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TravellerMapper {
    /**
     * Mapping travellerId to the id of {@link Traveller} entity
     *
     * @param travellerId to map
     * @return the {@link Traveller} with the id of the travellerId value
     */
    default Traveller fromTravellerId(Long travellerId) {
        if(travellerId == null) {
            return null;
        }

        final Traveller traveller = new Traveller();
        traveller.setId(travellerId);
        return traveller;

    }


    /**
     * Mapping {@link Traveller} to {@link TravellerDTO}
     *
     * @param traveller to map
     * @return the {@link PersonalInformationDTO} mapped from {@link Traveller}
     */
    TravellerDTO toTravellerDTO(Traveller traveller);



    /**
     * Mapping {@link TravellerSaveRequest} to {@link Traveller}
     *
     * @param travellerSaveRequest to map
     * @return the {@link Traveller} mapped from {@link TravellerSaveRequest}
     */
    Traveller fromTravellerSaveRequest(TravellerSaveRequest travellerSaveRequest);


//    /**
//     * Override {@link Traveller} with {@link TravellerSaveRequest}
//     *
//     * @param saveRequest the {@link TravellerSaveRequest} used to override {@link Traveller}
//     * @param traveller {@link Traveller} to be overwritten
//     */
//    @Mapping(source = "saveRequest.utilisateurId", target = "utilisateurId")
//    void overrideFromTravellerSaveRequest(TravellerSaveRequest saveRequest, @MappingTarget Traveller traveller);
}
