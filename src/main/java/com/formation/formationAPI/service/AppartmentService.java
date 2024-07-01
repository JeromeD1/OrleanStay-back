package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.AppartmentDTO;
import com.formation.formationAPI.models.entity.Appartment;
import com.formation.formationAPI.models.request.AppartmentSaveRequest;
import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface AppartmentService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<AppartmentDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @PermitAll
    List<AppartmentDTO> findAllActive();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<AppartmentDTO> findByOwnerId(Long ownerId);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @PermitAll
    AppartmentDTO findDTOById(Long ownerId);

    @Transactional(propagation = Propagation.REQUIRED)
    AppartmentDTO create(AppartmentSaveRequest appartmentSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    AppartmentDTO update(Long id, AppartmentSaveRequest appartmentSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);

}
