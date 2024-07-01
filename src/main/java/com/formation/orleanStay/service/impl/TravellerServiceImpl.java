package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.TravellerMapper;
import com.formation.orleanStay.models.DTO.TravellerDTO;
import com.formation.orleanStay.models.entity.PersonalInformation;
import com.formation.orleanStay.models.entity.Traveller;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.PersonalInformationSaveRequest;
import com.formation.orleanStay.models.request.TravellerSaveRequest;
import com.formation.orleanStay.repository.TravellerRepository;
import com.formation.orleanStay.service.PersonalInformationService;
import com.formation.orleanStay.service.TravellerService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TravellerServiceImpl implements TravellerService {

    final private TravellerRepository travellerRepository;
    final private TravellerMapper travellerMapper;

    final private PersonalInformationService personalInformationService;
    final private Findbyid findbyid;

    public TravellerServiceImpl(TravellerRepository travellerRepository, TravellerMapper travellerMapper, PersonalInformationService personalInformationService, Findbyid findbyid) {
        this.travellerRepository = travellerRepository;
        this.travellerMapper = travellerMapper;
        this.personalInformationService = personalInformationService;
        this.findbyid = findbyid;
    }


    @Override
    public List<TravellerDTO> findAll() {
        final List<Traveller> travellers = travellerRepository.findAll();
        return travellers.stream()
                .map(travellerMapper::toTravellerDTO)
                .toList();
    }

    @Override
    public TravellerDTO findDTOById(Long id) {
        final Traveller traveller = findbyid.findTravellerById(id);
        return travellerMapper.toTravellerDTO(traveller);
    }

    @Override
    public TravellerDTO createWithUtilisateur(TravellerSaveRequest travellerSaveRequest){
        //récupération des données de l'utilisateur
        final Utilisateur utilisateurOfRequest = findbyid.findUtilisateurById(travellerSaveRequest.getUtilisateurId());
        //comparaison des personalInformation de la request avec celles de l'utilisateur
        if(comparePersonalInformation(travellerSaveRequest.getPersonalInformations(), utilisateurOfRequest.getPersonalInformations())){
            //si les données sont identiques on ne crée pas de nouvelles personalInformation mais on récupère celles de l'utilisateur
            travellerSaveRequest.getPersonalInformations().setId(utilisateurOfRequest.getPersonalInformations().getId());
        } else {
            //création de la personalInformation et récupération de l'id
            final PersonalInformationSaveRequest personalInformationSaveRequest = travellerSaveRequest.getPersonalInformations();
            final PersonalInformation travellerSavedPersonalInformation = personalInformationService.createEntity(personalInformationSaveRequest);
            travellerSaveRequest.getPersonalInformations().setId(travellerSavedPersonalInformation.getId());
        }


        //Enregistrement du nouveau traveller en BDD et conversion en DTO à retourner
        final Traveller travellerToSave = travellerMapper.fromTravellerSaveRequest(travellerSaveRequest);
        //Ajout de l'utilisateur à travellerToSave
        travellerToSave.setUtilisateur(utilisateurOfRequest);

        Traveller savedTraveller = travellerRepository.save(travellerToSave);

        return travellerMapper.toTravellerDTO(savedTraveller);
    }

    @Override
    public Traveller createEntityWithUtilisateur(TravellerSaveRequest travellerSaveRequest){
        //récupération des données de l'utilisateur
        final Utilisateur utilisateurOfRequest = findbyid.findUtilisateurById(travellerSaveRequest.getUtilisateurId());
        //comparaison des personalInformation de la request avec celles de l'utilisateur
        if(comparePersonalInformation(travellerSaveRequest.getPersonalInformations(), utilisateurOfRequest.getPersonalInformations())){
            //si les données sont identiques on ne crée pas de nouvelles personalInformation mais on récupère celles de l'utilisateur
            travellerSaveRequest.getPersonalInformations().setId(utilisateurOfRequest.getPersonalInformations().getId());
        } else {
            //création de la personalInformation et récupération de l'id
            final PersonalInformationSaveRequest personalInformationSaveRequest = travellerSaveRequest.getPersonalInformations();
            final PersonalInformation travellerSavedPersonalInformation = personalInformationService.createEntity(personalInformationSaveRequest);
            travellerSaveRequest.getPersonalInformations().setId(travellerSavedPersonalInformation.getId());
        }


        //Enregistrement du nouveau traveller en BDD et conversion en DTO à retourner
        final Traveller travellerToSave = travellerMapper.fromTravellerSaveRequest(travellerSaveRequest);
        //Ajout de l'utilisateur à travellerToSave
        travellerToSave.setUtilisateur(utilisateurOfRequest);

        Traveller savedTraveller = travellerRepository.save(travellerToSave);

        return savedTraveller;
    }

    @Override
    public TravellerDTO createWithoutUtilisateur(TravellerSaveRequest travellerSaveRequest){
        //création de la personalInformation et récupération de l'id
        final PersonalInformationSaveRequest personalInformationSaveRequest = travellerSaveRequest.getPersonalInformations();
        final PersonalInformation travellerSavedPersonalInformation = personalInformationService.createEntity(personalInformationSaveRequest);
        travellerSaveRequest.getPersonalInformations().setId(travellerSavedPersonalInformation.getId());


        //Enregistrement du nouveau traveller en BDD et conversion en DTO à retourner
        final Traveller travellerToSave = travellerMapper.fromTravellerSaveRequest(travellerSaveRequest);
        Traveller savedTraveller = travellerRepository.save(travellerToSave);

        return travellerMapper.toTravellerDTO(savedTraveller);
    }

    @Override
    public Traveller createEntityWithoutUtilisateur(TravellerSaveRequest travellerSaveRequest){
        //création de la personalInformation et récupération de l'id
        final PersonalInformationSaveRequest personalInformationSaveRequest = travellerSaveRequest.getPersonalInformations();
        final PersonalInformation travellerSavedPersonalInformation = personalInformationService.createEntity(personalInformationSaveRequest);
        travellerSaveRequest.getPersonalInformations().setId(travellerSavedPersonalInformation.getId());


        //Enregistrement du nouveau traveller en BDD et conversion en DTO à retourner
        final Traveller travellerToSave = travellerMapper.fromTravellerSaveRequest(travellerSaveRequest);
        Traveller savedTraveller = travellerRepository.save(travellerToSave);

        return savedTraveller;
    }

    @Override
    public void delete(Long id) {
        final Traveller travellerToDelete = findbyid.findTravellerById(id);
        travellerRepository.delete(travellerToDelete);

        //si traveller.utilisateurId == null, on supprime personalInformation
        // sinon, si Traveller.personalInformationId != utilisateur.personalInformationId, on supprime aussi personalInformation
        // on ne supprime pas personalInformation si Traveller.personalInformationId == utilisateur.personalInformationId
        final PersonalInformation linkedPersonalInformation = findbyid.findPersonalInformationById(travellerToDelete.getPersonalInformations().getId());
        if(travellerToDelete.getUtilisateurId() == null){
            personalInformationService.delete(linkedPersonalInformation.getId());
        } else  {
            final Utilisateur linkedUtilisateur = findbyid.findUtilisateurById(travellerToDelete.getUtilisateurId());
            if(!Objects.equals(travellerToDelete.getPersonalInformations().getId(), linkedUtilisateur.getPersonalInformations().getId())){
                personalInformationService.delete(linkedPersonalInformation.getId());
            }
        }

    }



    @Override
    public TravellerDTO update(Long id, TravellerSaveRequest travellerSaveRequest) {
        final PersonalInformationSaveRequest personalInformationPartOfSaveRequest = travellerSaveRequest.getPersonalInformations();
        PersonalInformation actualPersonalInformation = findbyid.findPersonalInformationById(personalInformationPartOfSaveRequest.getId());
        PersonalInformation updatedPersonalInformation;
        //UPDATE ou CREATE de personal information
        //Si personalInformationPartOfSaveRequest != actualPersonalInformation
        //Si traveller.utilisateurId == null --> update
        //Si traveller.utilisateurId != null
            //--> si traveller.personalInformation.id != utilisateur.personalInformation.id --> update
            //--> si traveller.personalInformation.id == utilisateur.personalInformation.id --> create
        //sinon on ne modifie pas et on récupère juste l'ancienne valeur
            if (travellerSaveRequest.getUtilisateurId() == null) {
                updatedPersonalInformation = personalInformationService.updateEntity(personalInformationPartOfSaveRequest.getId(), personalInformationPartOfSaveRequest);
            } else {
                final Utilisateur linkedUtilisateur = findbyid.findUtilisateurById(travellerSaveRequest.getUtilisateurId());
                if (personalInformationPartOfSaveRequest.getId().equals(linkedUtilisateur.getPersonalInformations().getId())) {
                    //Création d'un nouvel objet personalInformationRequest sans id
                    PersonalInformationSaveRequest requestToSave = copyPersonalInformationSaveRequestWithoutId(personalInformationPartOfSaveRequest);
                    // Création d'une nouvelle instance de PersonalInformation pour la sauvegarde
                    updatedPersonalInformation = personalInformationService.createEntity(requestToSave);
                } else {
                    updatedPersonalInformation = personalInformationService.updateEntity(personalInformationPartOfSaveRequest.getId(), personalInformationPartOfSaveRequest);
                }
            }


        //RECUPERATION ET MISE A JOUR DE TRAVELLER
        final Traveller travellerToUpdate = findbyid.findTravellerById(travellerSaveRequest.getId());
        travellerToUpdate.setPersonalInformations(updatedPersonalInformation);
        Traveller savedTraveller = travellerRepository.save(travellerToUpdate);

        return travellerMapper.toTravellerDTO(savedTraveller);
    }

    @Override
    public Traveller updateEntity(Long id, TravellerSaveRequest travellerSaveRequest) {
        final PersonalInformationSaveRequest personalInformationPartOfSaveRequest = travellerSaveRequest.getPersonalInformations();
        PersonalInformation actualPersonalInformation = findbyid.findPersonalInformationById(personalInformationPartOfSaveRequest.getId());
        PersonalInformation updatedPersonalInformation;
        //UPDATE ou CREATE de personal information
        //Si personalInformationPartOfSaveRequest != actualPersonalInformation
        //Si traveller.utilisateurId == null --> update
        //Si traveller.utilisateurId != null
        //--> si traveller.personalInformation.id != utilisateur.personalInformation.id --> update
        //--> si traveller.personalInformation.id == utilisateur.personalInformation.id --> create
        //sinon on ne modifie pas et on récupère juste l'ancienne valeur
        if (travellerSaveRequest.getUtilisateurId() == null) {
            updatedPersonalInformation = personalInformationService.updateEntity(personalInformationPartOfSaveRequest.getId(), personalInformationPartOfSaveRequest);
        } else {
            final Utilisateur linkedUtilisateur = findbyid.findUtilisateurById(travellerSaveRequest.getUtilisateurId());
            if (personalInformationPartOfSaveRequest.getId().equals(linkedUtilisateur.getPersonalInformations().getId())) {
                //Création d'un nouvel objet personalInformationRequest sans id
                PersonalInformationSaveRequest requestToSave = copyPersonalInformationSaveRequestWithoutId(personalInformationPartOfSaveRequest);
                // Création d'une nouvelle instance de PersonalInformation pour la sauvegarde
                updatedPersonalInformation = personalInformationService.createEntity(requestToSave);
            } else {
                updatedPersonalInformation = personalInformationService.updateEntity(personalInformationPartOfSaveRequest.getId(), personalInformationPartOfSaveRequest);
            }
        }


        //RECUPERATION ET MISE A JOUR DE TRAVELLER
        final Traveller travellerToUpdate = findbyid.findTravellerById(travellerSaveRequest.getId());
        travellerToUpdate.setPersonalInformations(updatedPersonalInformation);
        Traveller savedTraveller = travellerRepository.save(travellerToUpdate);

        return savedTraveller;
    }


    private Boolean comparePersonalInformation(PersonalInformationSaveRequest request, PersonalInformation information){
        if (request == null || information == null) {
            return false;
        }
        return Objects.equals(request.getFirstname(), information.getFirstname()) &&
                Objects.equals(request.getLastname(), information.getLastname()) &&
                Objects.equals(request.getEmail(), information.getEmail()) &&
                Objects.equals(request.getPhone(), information.getPhone()) &&
                Objects.equals(request.getAddress(), information.getAddress()) &&
                Objects.equals(request.getCity(), information.getCity()) &&
                Objects.equals(request.getCountry(), information.getCountry());
    }

    private PersonalInformationSaveRequest copyPersonalInformationSaveRequestWithoutId(PersonalInformationSaveRequest personalInformationSaveRequest) {
        PersonalInformationSaveRequest copy = new PersonalInformationSaveRequest();
        copy.setFirstname(personalInformationSaveRequest.getFirstname());
        copy.setLastname(personalInformationSaveRequest.getLastname());
        copy.setEmail(personalInformationSaveRequest.getEmail());
        copy.setPhone(personalInformationSaveRequest.getPhone());
        copy.setAddress(personalInformationSaveRequest.getAddress());
        copy.setCity(personalInformationSaveRequest.getCity());
        copy.setCountry(personalInformationSaveRequest.getCountry());
        copy.setId(null);
        return copy;
    }


}
