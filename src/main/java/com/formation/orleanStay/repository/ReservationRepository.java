package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByAcceptedFalseAndCancelledFalse();

    List<Reservation> findByAppartmentOwnerAndAcceptedFalseAndCancelledFalse(Utilisateur owner);

    @Query("SELECT r FROM Reservation r WHERE r.traveller.utilisateur.id = :utilisateurId")
    List<Reservation> findByUtilisateurId(Long utilisateurId);
}
