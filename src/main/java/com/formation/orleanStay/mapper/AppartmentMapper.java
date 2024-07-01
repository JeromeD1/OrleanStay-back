package com.formation.orleanStay.mapper;
import com.formation.orleanStay.models.DTO.AppartmentDTO;
import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.request.AppartmentSaveRequest;
import org.mapstruct.*;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AppartmentMapper {



    /**
     * Mapping appartmentId to the id of {@link Appartment} entity
     *
     * @param appartmentId id to map
     * @return the {@link Appartment} with the appartmentId value as the id value
     */
    default Appartment fromAppartmentId(Long appartmentId) {
        if (appartmentId == null) {
            return null;
        }

        final Appartment appartment = new Appartment();
        appartment.setId(appartmentId);
        return appartment;
    }

    /**
     * Mapping {@link Appartment} to {@link AppartmentDTO}
     *
     * @param appartment to map
     * @return the {@link AppartmentDTO} mapped from {@link Appartment}
     */
    AppartmentDTO toAppartmentDTO(Appartment appartment);


    /**
     * Mapping {@link AppartmentSaveRequest} to {@link Appartment}
     *
     * @param appartmentSaveRequest to map
     * @return the {@link Appartment} mapped from {@link AppartmentSaveRequest}
     */
    Appartment fromAppartmentSaveRequest(AppartmentSaveRequest appartmentSaveRequest);



    /**
     * Override {@link Appartment} with {@link AppartmentSaveRequest}
     *
     * @param saveRequest the {@link AppartmentSaveRequest} used to override {@link Appartment}
     * @param appartment {@link Appartment} to be overwritten
     */
    @Mapping(source = "saveRequest.name", target = "name")
    @Mapping(source = "saveRequest.description", target = "description")
    @Mapping(source = "saveRequest.address", target = "address")
    @Mapping(source = "saveRequest.zipcode", target = "zipcode")
    @Mapping(source = "saveRequest.city", target = "city")
    @Mapping(source = "saveRequest.googleMapUrl", target = "googleMapUrl")
    @Mapping(source = "saveRequest.distanceCityCenter", target = "distanceCityCenter")
    @Mapping(source = "saveRequest.distanceTrain", target = "distanceTrain")
    @Mapping(source = "saveRequest.distanceTram", target = "distanceTram")
    @Mapping(source = "saveRequest.nightPrice", target = "nightPrice")
    @Mapping(source = "saveRequest.caution", target = "caution")
    @Mapping(source = "saveRequest.menageCourtSejour", target = "menageCourtSejour")
    @Mapping(source = "saveRequest.menageLongSejour", target = "menageLongSejour")
    @Mapping(source = "saveRequest.menageLongueDuree", target = "menageLongueDuree")
    @Mapping(source = "saveRequest.type", target = "type")
    @Mapping(source = "saveRequest.discounts", target = "discounts")
    @Mapping(source = "saveRequest.owner", target = "owner")
    @Mapping(source = "saveRequest.active", target = "active")
    void overrideFromAppartmentSaveRequest(AppartmentSaveRequest saveRequest, @MappingTarget Appartment appartment);

}


