package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.exception.unknown.UnknownPersonalInformationException;
import com.formation.formationAPI.mapper.PersonalInformationMapper;
import com.formation.formationAPI.models.DTO.PersonalInformationDTO;
import com.formation.formationAPI.models.entity.PersonalInformation;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.repository.PersonalInformationRepository;
import com.formation.formationAPI.service.PersonalInformationService;
import com.formation.formationAPI.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonalInformationServiceImpl implements PersonalInformationService {

    final private PersonalInformationRepository personalInformationRepository;
    final private PersonalInformationMapper personalInformationMapper;
    final private Findbyid findbyid;


    public PersonalInformationServiceImpl(PersonalInformationRepository personalInformationRepository, PersonalInformationMapper personalInformationMapper, Findbyid findbyid) {
        this.personalInformationRepository = personalInformationRepository;
        this.personalInformationMapper = personalInformationMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<PersonalInformationDTO> findAll() {
        final List<PersonalInformation> personalInformations = personalInformationRepository.findAll();
        return personalInformations.stream()
                .map(personalInformationMapper::toPersonalInformationDTO)
                .toList();
    }

    @Override
    public PersonalInformationDTO findDTOById(Long id) {
        final PersonalInformation personalInformation = findbyid.findPersonalInformationById(id);
        return personalInformationMapper.toPersonalInformationDTO(personalInformation);
    }

    @Override
    public PersonalInformationDTO create(PersonalInformationSaveRequest personalInformationSaveRequest){
        final PersonalInformation personalInformationToSave = personalInformationMapper.fromPersonalInformationSaveRequest(personalInformationSaveRequest);
        final PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformationToSave);
        return personalInformationMapper.toPersonalInformationDTO(savedPersonalInformation);
    }

    @Override
    public PersonalInformation createEntity(PersonalInformationSaveRequest personalInformationSaveRequest){
        System.out.println("personalInformationSaveRequest" + personalInformationSaveRequest);
        final PersonalInformation personalInformationToSave = personalInformationMapper.fromPersonalInformationSaveRequest(personalInformationSaveRequest);
        return personalInformationRepository.save(personalInformationToSave);
    }

    @Override
    public PersonalInformationDTO update(Long id, PersonalInformationSaveRequest personalInformationSaveRequest){
        if(personalInformationSaveRequest.getId() == null){
            personalInformationSaveRequest.setId(id);
        }
        final PersonalInformation personalInformationToUpdate = findbyid.findPersonalInformationById(id);
        personalInformationMapper.overrideFromPersonalInformationSaveRequest(personalInformationSaveRequest, personalInformationToUpdate);
        PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformationToUpdate);
        return personalInformationMapper.toPersonalInformationDTO(savedPersonalInformation);
    }

    @Override
    public PersonalInformation updateEntity(Long id, PersonalInformationSaveRequest personalInformationSaveRequest){
        if(personalInformationSaveRequest.getId() == null){
            personalInformationSaveRequest.setId(id);
        }
        final PersonalInformation personalInformationToUpdate = findbyid.findPersonalInformationById(id);
        personalInformationMapper.overrideFromPersonalInformationSaveRequest(personalInformationSaveRequest, personalInformationToUpdate);
        PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformationToUpdate);
        return savedPersonalInformation;
    }

    @Override
    public void delete(Long id) {
        final PersonalInformation personalInformationToDelete = findbyid.findPersonalInformationById(id);
        personalInformationRepository.delete(personalInformationToDelete);
    }

}
