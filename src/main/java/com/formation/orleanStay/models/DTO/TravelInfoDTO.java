package com.formation.orleanStay.models.DTO;

import com.formation.orleanStay.models.enumeration.ETravelInfoContentType;
import lombok.Data;

@Data
public class TravelInfoDTO {

    private Long id;
    private Long appartmentId;
    private String content;
    private ETravelInfoContentType contentType;
    private Integer positionOrder;
}
