package com.formation.formationAPI.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class InfoSaveRequest {

    private Long id;

    @NotNull
    private Integer positionOrder;

    @NotNull
    private Long appartmentId;

    @NotBlank
    private String info;

}
