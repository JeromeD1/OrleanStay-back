package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.InfoDTO;
import com.formation.formationAPI.models.DTO.ReservationDTO;
import com.formation.formationAPI.models.request.InfoSaveRequest;
import com.formation.formationAPI.models.request.ReservationSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface ReservationService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<ReservationDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    ReservationDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO create(ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    ReservationDTO update(Long id, ReservationSaveRequest reservationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
