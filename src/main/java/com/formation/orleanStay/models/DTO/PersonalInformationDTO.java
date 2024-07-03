package com.formation.orleanStay.models.DTO;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
public class PersonalInformationDTO {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private String address;

    private String zipcode;

    private String city;

    private String country;

}
