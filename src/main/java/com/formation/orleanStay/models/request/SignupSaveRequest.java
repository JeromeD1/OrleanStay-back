package com.formation.orleanStay.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupSaveRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

}
