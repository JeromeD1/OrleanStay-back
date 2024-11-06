package com.formation.orleanStay.models.request;

import com.formation.orleanStay.models.entity.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecoveryTokenRequest {
    private String token;
    private Utilisateur utilisateur;
}
