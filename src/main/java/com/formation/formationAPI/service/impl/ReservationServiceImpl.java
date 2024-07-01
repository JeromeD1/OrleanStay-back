package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.exception.unknown.UnknownAppartmentIdException;
import com.formation.formationAPI.exception.unknown.UnknownReservationException;
import com.formation.formationAPI.exception.unknown.UnknownTravellerException;
import com.formation.formationAPI.mapper.ReservationMapper;
import com.formation.formationAPI.models.DTO.PersonalInformationDTO;
import com.formation.formationAPI.models.DTO.ReservationDTO;
import com.formation.formationAPI.models.entity.*;
import com.formation.formationAPI.models.request.PersonalInformationSaveRequest;
import com.formation.formationAPI.models.request.ReservationSaveRequest;
import com.formation.formationAPI.models.request.TravellerSaveRequest;
import com.formation.formationAPI.repository.AppartmentRepository;
import com.formation.formationAPI.repository.ReservationRepository;
import com.formation.formationAPI.service.PersonalInformationService;
import com.formation.formationAPI.service.ReservationService;
import com.formation.formationAPI.service.TravellerService;
import com.formation.formationAPI.utils.Findbyid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    final private ReservationRepository reservationRepository;
    final private ReservationMapper reservationMapper;
    final private TravellerService travellerService;
    final private Findbyid findbyid;
    final private PersonalInformationService personalInformationService;

    @PersistenceContext
    private EntityManager entityManager;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper, TravellerService travellerService, Findbyid findbyid, PersonalInformationService personalInformationService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.travellerService = travellerService;
        this.findbyid = findbyid;
        this.personalInformationService = personalInformationService;
    }


    @Override
    public List<ReservationDTO> findAll(){
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public ReservationDTO findDTOById(Long id){
        final Reservation reservation = findbyid.findReservationById(id);
        return reservationMapper.toReservationDTO(reservation);
    }

    @Override
    public ReservationDTO create(ReservationSaveRequest reservationSaveRequest){
        //création du traveller et récupération de son id
        final TravellerSaveRequest travellerSaveRequest = reservationSaveRequest.getTraveller();
        Traveller reservationSavedTraveller;
        if(travellerSaveRequest.getUtilisateurId() == null){
            reservationSavedTraveller = travellerService.createEntityWithoutUtilisateur(travellerSaveRequest);
        } else {
            reservationSavedTraveller = travellerService.createEntityWithUtilisateur(travellerSaveRequest);
        }

        //Création d'un objet Reservation à partir de la request
        final Reservation reservationToSave = reservationMapper.fromReservationSaveRequest(reservationSaveRequest);
        //Ajout de l'appartement à la réservation
        final Appartment appartment = findbyid.findAppartmentById(reservationSaveRequest.getAppartmentId());
        reservationToSave.setAppartment(appartment);
        //Ajout également du traveller à la réservation
        reservationToSave.setTraveller(reservationSavedTraveller);

        //Enregistrement du nouveau traveller en BDD et conversion en DTO à retourner
        final Reservation savedReservation = reservationRepository.save(reservationToSave);

        return reservationMapper.toReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO update(Long id, ReservationSaveRequest reservationSaveRequest) {

        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());
//        entityManager.merge(updatedTraveller);
//        entityManager.flush();
//        entityManager.clear();

        //RECUPERATION DE L'appartment
        final Appartment appartmentToUpdate = findbyid.findAppartmentById(reservationSaveRequest.getAppartmentId());

        //RECUPERATION DE LA RESERVATION
        final Reservation reservationToUpdate = findbyid.findReservationById(id);
        //UPDATE DE LA RESERVATION
        //il faut mettre à jour l'id de traveller.personalInformation car si elle a changé lors de la MAJ de traveller, ça bug autrement
        reservationSaveRequest.getTraveller().getPersonalInformations().setId(updatedTraveller.getPersonalInformations().getId());
        reservationMapper.overrideFromReservationSaveRequest(reservationSaveRequest, reservationToUpdate);
        reservationToUpdate.setTraveller(updatedTraveller);
        reservationToUpdate.setAppartment(appartmentToUpdate);
        //SAUVEGARDE DE LA RESERVATION
        final Reservation savedReservation = reservationRepository.save(reservationToUpdate);

        //RETURN DE LA RESERVATION MODIFIEE
        return reservationMapper.toReservationDTO(savedReservation);
    }

    @Override
    public void delete(Long id){
        final Reservation reservationToDelete = findbyid.findReservationById(id);
        reservationRepository.delete(reservationToDelete);
    }


}
