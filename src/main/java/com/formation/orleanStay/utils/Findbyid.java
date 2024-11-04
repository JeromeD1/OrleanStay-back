package com.formation.orleanStay.utils;

import com.formation.orleanStay.exception.unknown.*;
import com.formation.orleanStay.models.entity.*;
import com.formation.orleanStay.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class Findbyid {
    private final AppartmentRepository appartmentRepository;
    private final DiscountRepository discountRepository;
    private final InfoRepository infoRepository;
    private final PersonalInformationRepository personalInformationRepository;
    private final PhotoRepository photoRepository;
    private final ReservationRepository reservationRepository;
    private final TravellerRepository travellerRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackAnswerRepository feedbackAnswerRepository;
    private final ReservationChatRepository reservationChatRepository;
    private final TravelInfoRepository travelInfoRepository;


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

    public TravelInfo findTravelInfoById(Long id) {
        return travelInfoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Aucun TravelInfo trouvé avec l'id {}", id);
                    return new UnknownTravelInfoIdException(id);
                });
    }
}
