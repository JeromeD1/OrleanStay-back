package com.formation.orleanStay.models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class InfoDTO {
    private Long id;

    private Integer positionOrder;

    private Long appartmentId;

    private String info;
}
