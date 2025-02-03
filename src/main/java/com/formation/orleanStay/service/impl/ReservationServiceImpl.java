package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mailjet.EmailService;
import com.formation.orleanStay.mapper.ReservationMapper;
import com.formation.orleanStay.models.DTO.ReservationDTO;
import com.formation.orleanStay.models.entity.*;
import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.Traveller;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import com.formation.orleanStay.models.request.ReservationSaveRequest;
import com.formation.orleanStay.models.request.TravellerSaveRequest;
import com.formation.orleanStay.repository.ReservationRepository;
import com.formation.orleanStay.repository.ReservationRepositoryCustom;
import com.formation.orleanStay.service.PersonalInformationService;
import com.formation.orleanStay.service.ReservationService;
import com.formation.orleanStay.service.TravellerService;
import com.formation.orleanStay.utils.Findbyid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationRepositoryCustom reservationRepositoryCustom;
    private final ReservationMapper reservationMapper;
    private final TravellerService travellerService;
    private final Findbyid findbyid;
    private final EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${mailjet.resa.accepte.template.id}")
    private String templateResaAccepteId;

    @Value("${mailjet.demande.resa.template.id}")
    private String templateDemandeResaId;

    @Value("${mailjet.resa.refusee.template.id}")
    private String templateResaRefuseeId;

    @Value("${mailjet.resa.annulee.template.id}")
    private String templateResaAnnuleeId;

    @Value("${mailjet.demande.arrhes.template.id}")
    private String templateDemandeArrhesId;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationRepositoryCustom reservationRepositoryCustom, ReservationMapper reservationMapper, TravellerService travellerService, Findbyid findbyid, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.reservationRepositoryCustom = reservationRepositoryCustom;
        this.reservationMapper = reservationMapper;
        this.travellerService = travellerService;
        this.findbyid = findbyid;
        this.emailService = emailService;
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
    public List<ReservationDTO> findAllReservationRequests() {
        final List<Reservation> reservations = reservationRepository.findByAcceptedFalseAndCancelledFalse();
        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findbyUserId(Long userId) {
        final List<Reservation> reservations = reservationRepository.findByUtilisateurId(userId);
        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findReservationRequestsByOwnerId(Long ownerId) {
        final Utilisateur owner = findbyid.findUtilisateurById(ownerId);
        final List<Reservation> reservations = reservationRepository.findByAppartmentOwnerAndAcceptedFalseAndCancelledFalse(owner);
        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
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

        if(Boolean.FALSE.equals(savedReservation.getAccepted())){
            final String recipientEmail1 = savedReservation.getTraveller().getPersonalInformations().getEmail();
            final String recipientName1 = savedReservation.getTraveller().getPersonalInformations().getFirstname() + " " + savedReservation.getTraveller().getPersonalInformations().getLastname();
            final String subject1 = "Votre demande de reservation du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
            final Map<String, String> variables1 = emailService.makeEmailDemandeResaData(savedReservation, false);
            emailService.sendEmail(recipientEmail1, recipientName1, subject1, templateDemandeResaId, variables1);

            final String recipientEmail2 = savedReservation.getAppartment().getOwner().getPersonalInformations().getEmail();
            final String recipientName2 = savedReservation.getAppartment().getOwner().getPersonalInformations().getFirstname();
            final String subject2 = "Nouvelle demande : " + savedReservation.getAppartment().getName() + " du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
            final Map<String, String> variables2 = emailService.makeEmailDemandeResaData(savedReservation, true);
            emailService.sendEmail(recipientEmail2, recipientName2, subject2, templateDemandeResaId, variables2);

        }

        return reservationMapper.toReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO update(Long id, ReservationSaveRequest reservationSaveRequest) {

        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());

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
    public ReservationDTO cancelFromTraveller(Long id, ReservationSaveRequest reservationSaveRequest) {
        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());

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

        //envoi mail
        final String recipientEmail1 = savedReservation.getTraveller().getPersonalInformations().getEmail();
        final String recipientName1 = savedReservation.getTraveller().getPersonalInformations().getFirstname() + " " + savedReservation.getTraveller().getPersonalInformations().getLastname();
        final String subject1 = "Réservation annulée du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
        final Map<String, String> variables1 = emailService.makeEmailResaAnnuleeData(savedReservation, false);
        emailService.sendEmail(recipientEmail1, recipientName1, subject1, templateResaAnnuleeId, variables1);

        final String recipientEmail2 = savedReservation.getAppartment().getOwner().getPersonalInformations().getEmail();
        final String recipientName2 = savedReservation.getAppartment().getOwner().getPersonalInformations().getFirstname();
        final String subject2 = "Réservation annulée : " + savedReservation.getAppartment().getName() + " du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
        final Map<String, String> variables2 = emailService.makeEmailResaAnnuleeData(savedReservation, true);
        emailService.sendEmail(recipientEmail2, recipientName2, subject2, templateResaAnnuleeId, variables2);


        //RETURN DE LA RESERVATION MODIFIEE
        return reservationMapper.toReservationDTO(savedReservation);
    }

    public ReservationDTO askForDeposit(Long id, ReservationSaveRequest reservationSaveRequest) {
        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());

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

        //send Email
        final String recipientEmail = savedReservation.getTraveller().getPersonalInformations().getEmail();
        final String recipientName = savedReservation.getTraveller().getPersonalInformations().getFirstname() + " " + savedReservation.getTraveller().getPersonalInformations().getLastname();
        final String subject = "Payez les arrhes pour valider votre réservation du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
        final Map<String, String> variables = emailService.makeEmailDemandeArrhesData(savedReservation);
        emailService.sendEmail(recipientEmail, recipientName, subject, templateDemandeArrhesId, variables);
        //RETURN DE LA RESERVATION MODIFIEE
        return reservationMapper.toReservationDTO(savedReservation);
    }

    public ReservationDTO rejectReservation(Long id, ReservationSaveRequest reservationSaveRequest) {
        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());

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

        //send Email
        final String recipientEmail = savedReservation.getTraveller().getPersonalInformations().getEmail();
        final String recipientName = savedReservation.getTraveller().getPersonalInformations().getFirstname() + " " + savedReservation.getTraveller().getPersonalInformations().getLastname();
        final String subject = "Réservation refusée";
        final Map<String, String> variables = emailService.makeEmailResaAccepteeData(savedReservation);
        emailService.sendEmail(recipientEmail, recipientName, subject, templateResaRefuseeId, variables);
        //RETURN DE LA RESERVATION MODIFIEE
        return reservationMapper.toReservationDTO(savedReservation);
    }

    public ReservationDTO acceptReservation(Long id, ReservationSaveRequest reservationSaveRequest) {

        //RECUPERATION OU MISE A JOUR DE TRAVELLER
        Traveller updatedTraveller = findbyid.findTravellerById(reservationSaveRequest.getTraveller().getId());

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

        //send Email
        final String recipientEmail = savedReservation.getTraveller().getPersonalInformations().getEmail();
        final String recipientName = savedReservation.getTraveller().getPersonalInformations().getFirstname() + " " + savedReservation.getTraveller().getPersonalInformations().getLastname();
        final String subject = "Votre reservation à Orléans du " + savedReservation.getFormatedCheckinDate() + " au " + savedReservation.getFormatedCheckoutDate();
        final Map<String, String> variables = emailService.makeEmailResaAccepteeData(savedReservation);
        emailService.sendEmail(recipientEmail, recipientName, subject, templateResaAccepteId, variables);
        //RETURN DE LA RESERVATION MODIFIEE
        return reservationMapper.toReservationDTO(savedReservation);
    }

    @Override
    public void delete(Long id){
        final Reservation reservationToDelete = findbyid.findReservationById(id);
        reservationRepository.delete(reservationToDelete);
    }

    @Override
    public List<ReservationDTO> findFilteredReservationsForReservationChatAnswering(Long userId) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneMonthAgo = now.minusMonths(1);
        final List<Reservation> reservations = reservationRepository.findFilteredReservationsForReservationChatAnswering(oneMonthAgo, userId);

        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findwithCheckoutDateLaterThanOneMonthAgo() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneMonthAgo = now.minusMonths(1);
        final List<Reservation> reservations = reservationRepository.findwithCheckoutDateLaterThanOneMonthAgo(oneMonthAgo);

        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findwithCriteria(ReservationResearchRequest reservationResearchRequest) {
        List<Reservation> reservations = reservationRepositoryCustom.findReservationsByCriteria(reservationResearchRequest);

        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
    }

    @Override
    public Long sendInfoTravelEmail(Long reservationId) {
        final Reservation reservation = findbyid.findReservationById(reservationId);
        final String recipientEmail = reservation.getTraveller().getPersonalInformations().getEmail();
        final String recipientName = reservation.getTraveller().getPersonalInformations().getFirstname() + " " + reservation.getTraveller().getPersonalInformations().getLastname();
        final String subject = "Votre reservation à Orléans du " + reservation.getFormatedCheckinDate() + " au " + reservation.getFormatedCheckoutDate();
        final Map<String, String> variables = emailService.makeEmailResaAccepteeData(reservation);
        emailService.sendEmail(recipientEmail, recipientName, subject, templateResaAccepteId, variables);

        return reservation.getId();
    }

}
