package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.TravellerDTO;
import com.formation.orleanStay.models.entity.Traveller;
import com.formation.orleanStay.models.request.TravellerSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface TravellerService {
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<TravellerDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    TravellerDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    TravellerDTO createWithUtilisateur(TravellerSaveRequest travellerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    TravellerDTO createWithoutUtilisateur(TravellerSaveRequest travellerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    Traveller createEntityWithUtilisateur(TravellerSaveRequest travellerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    Traveller createEntityWithoutUtilisateur(TravellerSaveRequest travellerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    TravellerDTO update(Long id, TravellerSaveRequest travellerSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    Traveller updateEntity(Long id, TravellerSaveRequest travellerSaveRequest);

//    @Transactional(propagation = Propagation.REQUIRED)
//    Traveller justSave(Traveller travellerToSave);


    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
