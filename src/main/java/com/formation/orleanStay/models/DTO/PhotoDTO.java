package com.formation.orleanStay.models.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@EqualsAndHashCode
@Data
public class PhotoDTO {
    private Long id;

    private Integer positionOrder;

    private Long appartmentId;

    private String imgUrl;

}
