package com.formation.orleanStay.models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class DiscountDTO {
    private Long id;

    private BigDecimal week;

    private BigDecimal month;

    private boolean weekActivated;

    private boolean monthActivated;
}
