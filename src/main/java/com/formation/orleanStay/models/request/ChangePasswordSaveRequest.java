package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordSaveRequest {
    @NotBlank
    @Size(min = 8)
    private String oldPassword;
    @NotBlank
    @Size(min = 8)
    private String newPassword;
    @NotBlank
    @Size(min = 8)
    private String confirmationPassword;
}
