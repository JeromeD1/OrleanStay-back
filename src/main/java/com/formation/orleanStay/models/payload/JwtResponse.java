package com.formation.orleanStay.models.payload;

import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UtilisateurDTO utilisateur;


    public JwtResponse(String token, UtilisateurDTO utilisateur) {
        this.token = token;
        this.utilisateur = utilisateur;
    }
}
