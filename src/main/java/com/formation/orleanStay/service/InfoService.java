package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.InfoDTO;
import com.formation.orleanStay.models.request.InfoListSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface InfoService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<InfoDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    InfoDTO findDTOById(Long id);

//    @Transactional(propagation = Propagation.REQUIRED)
//    InfoDTO create(InfoSaveRequest infoSaveRequest);

//    @Transactional(propagation = Propagation.REQUIRED)
//    InfoDTO update(Long id, InfoSaveRequest infoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    List<InfoDTO> update(Long appartmentId, InfoListSaveRequest infosToRegister);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);



}
