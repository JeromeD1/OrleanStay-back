package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.TravelInfoDTO;
import com.formation.orleanStay.models.entity.TravelInfo;
import com.formation.orleanStay.models.request.TravelInfoSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TravelInfoMapper {
    /**
     * Mapping travelInfoId to the id of {@link TravelInfo} entity
     *
     * @param travelInfoId to map
     * @return the {@link TravelInfo} with the id of the travelInfoId value
     */
    default TravelInfo fromTravelInfoId(Long travelInfoId) {
        if(travelInfoId == null) {
            return null;
        }

        final TravelInfo travelInfo = new TravelInfo();
        travelInfo.setId(travelInfoId);
        return travelInfo;
    }


    /**
     * Mapping {@link TravelInfo} to {@link TravelInfoDTO}
     *
     * @param travelInfo to map
     * @return the {@link TravelInfoDTO} mapped from {@link TravelInfo}
     */
    TravelInfoDTO toTravelInfoDTO(TravelInfo travelInfo);



    /**
     * Mapping {@link TravelInfoSaveRequest} to {@link TravelInfo}
     *
     * @param travelInfoSaveRequest to map
     * @return the {@link TravelInfo} mapped from {@link TravelInfoSaveRequest}
     */
    TravelInfo fromTravelInfoSaveRequest(TravelInfoSaveRequest travelInfoSaveRequest);


    /**
     * Override {@link TravelInfo} with {@link TravelInfoSaveRequest}
     *
     * @param saveRequest the {@link TravelInfoSaveRequest} used to override {@link TravelInfo}
     * @param travelInfo {@link TravelInfo} to be overwritten
     */
    @Mapping(source = "saveRequest.content", target = "content")
    @Mapping(source = "saveRequest.contentType", target = "contentType")
    @Mapping(source = "saveRequest.positionOrder", target = "positionOrder")
    void overrideFromTravelInfoSaveRequest(TravelInfoSaveRequest saveRequest, @MappingTarget TravelInfo travelInfo);
}
