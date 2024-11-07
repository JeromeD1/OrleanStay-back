package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByAcceptedFalseAndCancelledFalse();

    List<Reservation> findByAppartmentOwnerAndAcceptedFalseAndCancelledFalse(Utilisateur owner);

    @Query("SELECT r FROM Reservation r WHERE r.traveller.utilisateur.id = :utilisateurId")
    List<Reservation> findByUtilisateurId(Long utilisateurId);

    @Query("SELECT r FROM Reservation r " +
            "JOIN ReservationChat rc ON r.id = rc.reservation.id " +
            "WHERE r.checkoutDate > :lastMonth " +
            "AND rc.id = (SELECT MAX(rc2.id) " +
            "FROM ReservationChat rc2 " +
            "WHERE rc2.reservation.id = r.id) " +
            "AND rc.utilisateur.id != :utilisateurId")
    List<Reservation> findFilteredReservationsForReservationChatAnswering(@Param("lastMonth") LocalDateTime lastMonth, @Param("utilisateurId") Long utilisateurId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.checkoutDate > :lastMonth ")
    List<Reservation> findwithCheckoutDateLaterThanOneMonthAgo(@Param("lastMonth") LocalDateTime lastMonth);

}
