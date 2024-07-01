package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.InfoDTO;
import com.formation.formationAPI.models.entity.Info;
import com.formation.formationAPI.models.request.InfoSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InfoMapper {
    /**
     * Mapping infoId to the id of {@link Info} entity
     *
     * @param infoId to map
     * @return the {@link Info} with the id of the infoId value
     */
    default Info fromInfoId(Long infoId) {
        if(infoId == null) {
            return null;
        }

        final Info info = new Info();
        info.setId(infoId);
        return info;
    }


    /**
     * Mapping {@link Info} to {@link InfoDTO}
     *
     * @param info to map
     * @return the {@link InfoDTO} mapped from {@link Info}
     */
    InfoDTO toInfoDTO(Info info);



    /**
     * Mapping {@link InfoSaveRequest} to {@link Info}
     *
     * @param infoSaveRequest to map
     * @return the {@link Info} mapped from {@link InfoSaveRequest}
     */
    Info fromInfoSaveRequest(InfoSaveRequest infoSaveRequest);


    /**
     * Override {@link Info} with {@link InfoSaveRequest}
     *
     * @param saveRequest the {@link InfoSaveRequest} used to override {@link Info}
     * @param info {@link Info} to be overwritten
     */
    @Mapping(source = "saveRequest.info", target = "info")
    @Mapping(source = "saveRequest.positionOrder", target = "positionOrder")
    void overrideFromInfoSaveRequest(InfoSaveRequest saveRequest, @MappingTarget Info info);

}
