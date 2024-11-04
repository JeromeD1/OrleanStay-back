package com.formation.orleanStay.models.request;

import com.formation.orleanStay.models.enumeration.ETravelInfoContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TravelInfoSaveRequest {
    private Long id;
    @NotNull
    private Long appartmentId;
    @NotBlank
    private String content;
    @NotBlank
    private ETravelInfoContentType contentType;
    @NotNull
    private Integer positionOrder;
}
