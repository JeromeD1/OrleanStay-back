package com.formation.orleanStay.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
public class RefreshToken {
    private String token;
    private Duration expiryDate;
}
