package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.UtilisateurDTO;
import com.formation.orleanStay.models.enumeration.ERole;
import com.formation.orleanStay.models.request.ChangePasswordSaveRequest;
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

    @Transactional(propagation = Propagation.REQUIRED)
    UtilisateurDTO updateRole(Long id, ERole newRole);

    @Transactional(propagation = Propagation.REQUIRED)
    Long updatePassword(Long id, ChangePasswordSaveRequest changePasswordSaveRequest);
}
