package com.formation.orleanStay.utils;

import com.formation.orleanStay.exception.unknown.*;
import com.formation.orleanStay.models.entity.*;
import com.formation.orleanStay.repository.*;
import com.formation.orleanStay.exception.unknown.*;
import com.formation.orleanStay.models.entity.*;
import com.formation.orleanStay.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Findbyid {
    final private AppartmentRepository appartmentRepository;
    final private DiscountRepository discountRepository;
    final private InfoRepository infoRepository;
    final private PersonalInformationRepository personalInformationRepository;
    final private PhotoRepository photoRepository;
    final private ReservationRepository reservationRepository;
    final private TravellerRepository travellerRepository;
    final private UtilisateurRepository utilisateurRepository;
    final private FeedbackRepository feedbackRepository;
    final private FeedbackAnswerRepository feedbackAnswerRepository;
    final private ReservationChatRepository reservationChatRepository;
    final private PrivatePhotoRepository privatePhotoRepository;

    public Findbyid(AppartmentRepository appartmentRepository, DiscountRepository discountRepository, InfoRepository infoRepository, PersonalInformationRepository personalInformationRepository, PhotoRepository photoRepository, ReservationRepository reservationRepository, TravellerRepository travellerRepository, UtilisateurRepository utilisateurRepository, FeedbackRepository feedbackRepository, FeedbackAnswerRepository feedbackAnswerRepository, ReservationChatRepository reservationChatRepository, PrivatePhotoRepository privatePhotoRepository) {
        this.appartmentRepository = appartmentRepository;
        this.discountRepository = discountRepository;
        this.infoRepository = infoRepository;
        this.personalInformationRepository = personalInformationRepository;
        this.photoRepository = photoRepository;
        this.reservationRepository = reservationRepository;
        this.travellerRepository = travellerRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackAnswerRepository = feedbackAnswerRepository;
        this.reservationChatRepository = reservationChatRepository;
        this.privatePhotoRepository = privatePhotoRepository;
    }


    public Reservation findReservationById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune réservation trouvée avec l'id {}", id);
                    return new UnknownReservationException(id);
                });
    }

     public Appartment findAppartmentById(Long appartmentId){
        return appartmentRepository.findById(appartmentId)
                .orElseThrow(() -> {
                    log.warn("Aucun appartment trouvé avec l'id {}", appartmentId);
                    return new UnknownAppartmentIdException(appartmentId);
                });
    }

    public Info findInfoById(Long id){
        return infoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune info trouvée avec l'id {}", id);
                    return new UnknownInfoIdException(id);
                });
    }

    public Discount findDiscountById(Long id){
        return discountRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Aucun discount trouvé avec l'id {}", id);
                            return new UnknownDiscountIdException(id);
                        }
                );
    }

    public PersonalInformation findPersonalInformationById(Long id){
        return personalInformationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune personalInformation trouvée avec l'id {}", id);
                    return new UnknownPersonalInformationException(id);
                });
    }

    public Photo findPhotoById(Long id){
        return photoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune info trouvée avec l'id {}", id);
                    return new UnknownPhotoIdException(id);
                });
    }

    public Traveller findTravellerById(Long id){
        return travellerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucun traveller trouvé avec l'id {}", id);
                    return new UnknownTravellerException(id);
                });
    }

    public Utilisateur findUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucun utilisateur trouvé avec l'id {}", id);
                    return new UnknownUtilisateurIdException(id);
                });
    }

    public boolean verifyUtilisateurExistByLogin(String login) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
        return utilisateur != null;
    }

    public Utilisateur findUtilisateurByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    public Feedback findFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucun commentaire trouvé avec l'id {}", id);
                    return new UnknownFeedbackIdException(id);
                });
    }

    public FeedbackAnswer findFeedbackAnswerById(Long id) {
        return feedbackAnswerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune réponse de commentaire trouvée avec l'id {}", id);
                    return new UnknownFeedbackAnswerIdException(id);
                });
    }

    public ReservationChat findReservationChatById(Long id) {
        return reservationChatRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucun chat de reservation trouvé avec l'id {}", id);
                    return new UnknownReservationChatIdException(id);
                });
    }

    public PrivatePhoto findPrivatePhotoById(Long id) {
        return privatePhotoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucune private photo trouvée avec l'id {}", id);
                    return new UnknownPrivatePhotoIdException(id);
                });
    }
}
