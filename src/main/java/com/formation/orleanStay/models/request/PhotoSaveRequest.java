package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PhotoSaveRequest {
    private Long id;

    @NotNull
    private Integer positionOrder;

    @NotNull
    private Long appartmentId;

    @NotBlank
    private String imgUrl;

}
