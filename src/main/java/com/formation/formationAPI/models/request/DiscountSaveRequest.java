package com.formation.formationAPI.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
//@Accessors(fluent = true) //pour que lombok cree des getters et setters adequats pour les variables isWeekActivated et isMonthActivated
public class DiscountSaveRequest {

    private Long id;

    @NotNull
    private BigDecimal week;

    @NotNull
    private BigDecimal month;

    @NotNull
    private boolean weekActivated;

    @NotNull
    private boolean monthActivated;

}
