package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f WHERE f.utilisateur.id = :utilisateurId AND f.appartment.id = :appartmentId")
    List<Feedback> findFeedbackByUtilisateurIdAndAppartmentId(@Param("utilisateurId") Long utilisateurId, @Param("appartmentId") Long appartmentId);

    @Query("SELECT f FROM Feedback f WHERE f.reservation.id = :reservationId")
    List<Feedback> findFeedbackByReservationId(@Param("reservationId") Long reservationId);

}
