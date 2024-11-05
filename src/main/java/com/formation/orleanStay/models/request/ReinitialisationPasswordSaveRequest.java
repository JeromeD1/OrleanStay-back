package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReinitialisationPasswordSaveRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String confirmationPassword;
    @NotBlank
    private String token;
}
