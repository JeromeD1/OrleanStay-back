package com.formation.orleanStay.models.DTO;

import lombok.Data;

@Data
public class AppartmentNameAndOwnerDTO {
    private Long id;
    private String name;
    private Long ownerId;
}
