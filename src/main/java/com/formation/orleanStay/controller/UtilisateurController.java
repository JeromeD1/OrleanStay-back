package com.formation.orleanStay.controller;

import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.enumeration.ERole;
import com.formation.orleanStay.models.request.ChangePasswordSaveRequest;
import com.formation.orleanStay.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("utilisateurs")
@Slf4j
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findAll(){
        log.info("Envoi de tous les utilisateurs");
        return utilisateurService.findAll();
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findOwners(){
        log.info("Envoi de tous les propriétaires");
        return utilisateurService.findByRoleOwner();
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findUsers(){
        log.info("Envoi de tous les utilisateurs de type USER");
        return utilisateurService.findByRoleUser();
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findAdmin(){
        log.info("Envoi de tous les admin");
        return utilisateurService.findByRoleAdmin();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurDTO findById(@PathVariable Long id) {
        log.info("Envoi de l'utilisateur avec l'id : {}", id);
        return utilisateurService.findDTOById(id);
    }

    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurDTO updateRole(@PathVariable Long id, @RequestBody Map<String, String> roleMap) {
        String newRoleStr = roleMap.get("newRole");
        ERole newRole = ERole.valueOf(newRoleStr);
        log.info("Modification du role de l'utilisateur avec l'id {} et le role {}", id, newRole);
        return utilisateurService.updateRole(id, newRole);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public Long updatePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordSaveRequest changePasswordSaveRequest) {
        log.info("Modification du mot de passe de l'utilisateur avec l'id {}", id);
        return utilisateurService.updatePassword(id, changePasswordSaveRequest);
    }

}
