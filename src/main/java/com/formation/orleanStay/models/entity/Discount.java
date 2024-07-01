package com.formation.orleanStay.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "Decimal(3,2) default '1.00'")
    @NotNull
    @Digits(integer = 1, fraction = 2)
    private BigDecimal week;

    @Column(nullable = false, columnDefinition = "Decimal(3,2) default '1.00'")
    @NotNull
    @Digits(integer = 1, fraction = 2)
    private BigDecimal month;

    @Column(name = "is_week_activated",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @NotNull
    @JsonProperty("isWeekActivated")
    private boolean weekActivated;

    @Column(name = "is_month_activated", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @NotNull
    @JsonProperty("isMonthActivated")
    private boolean monthActivated;

}
