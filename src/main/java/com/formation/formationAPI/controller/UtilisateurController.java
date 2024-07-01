package com.formation.formationAPI.controller;

import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import com.formation.formationAPI.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.debug("Envoi de tous les utilisateurs");
        return utilisateurService.findAll();
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findOwners(){
        log.debug("Envoi de tous les propriétaires");
        return utilisateurService.findByRoleOwner();
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findUsers(){
        log.debug("Envoi de tous les utilisateurs de type USER");
        return utilisateurService.findByRoleUser();
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurDTO> findAdmin(){
        log.debug("Envoi de tous les admin");
        return utilisateurService.findByRoleAdmin();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurDTO findById(@PathVariable Long id) {
        log.debug("Envoi de l'utilisateur avec l'id : {}", id);
        return utilisateurService.findDTOById(id);
    }

}
