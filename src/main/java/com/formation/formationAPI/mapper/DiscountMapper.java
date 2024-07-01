package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.DiscountDTO;
import com.formation.formationAPI.models.entity.Discount;
import com.formation.formationAPI.models.request.DiscountSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiscountMapper {
    /**
     * Mapping discountId to the id of {@link Discount} entity
     *
     * @param discountId to map
     * @return the {@link Discount} with the id of the discountId value
     */
    default Discount fromDiscountId(Long discountId) {
        if(discountId == null) {
            return null;
        }

        final Discount discounts = new Discount();
        discounts.setId(discountId);
        return discounts;

    }


    /**
     * Mapping {@link Discount} to {@link DiscountDTO}
     *
     * @param discounts to map
     * @return the {@link DiscountDTO} mapped from {@link Discount}
     */
    DiscountDTO toDiscountDTO(Discount discounts);



    /**
     * Mapping {@link DiscountSaveRequest} to {@link Discount}
     *
     * @param discountSaveRequest to map
     * @return the {@link Discount} mapped from {@link DiscountSaveRequest}
     */
    Discount fromDiscountSaveRequest(DiscountSaveRequest discountSaveRequest);


    /**
     * Override {@link Discount} with {@link DiscountSaveRequest}
     *
     * @param saveRequest the {@link DiscountSaveRequest} used to override {@link Discount}
     * @param discounts {@link Discount} to be overwritten
     */
    @Mapping(source = "saveRequest.week", target = "week")
    @Mapping(source = "saveRequest.month", target = "month")
    @Mapping(source = "saveRequest.weekActivated", target = "weekActivated")
    @Mapping(source = "saveRequest.monthActivated", target = "monthActivated")
    void overrideFromDiscountSaveRequest(DiscountSaveRequest saveRequest, @MappingTarget Discount discounts);



}


