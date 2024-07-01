package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.UtilisateurDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UtilisateurService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<UtilisateurDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    UtilisateurDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<UtilisateurDTO> findByRoleOwner();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<UtilisateurDTO> findByRoleUser();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<UtilisateurDTO> findByRoleAdmin();
}
