package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.PersonalInformationDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.request.PersonalInformationSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PersonalInformationService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<PersonalInformationDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    PersonalInformationDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    PersonalInformationDTO create(PersonalInformationSaveRequest personalInformationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    PersonalInformation createEntity(PersonalInformationSaveRequest personalInformationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    PersonalInformationDTO update(Long id, PersonalInformationSaveRequest personalInformationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    PersonalInformation updateEntity(Long id, PersonalInformationSaveRequest personalInformationSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
