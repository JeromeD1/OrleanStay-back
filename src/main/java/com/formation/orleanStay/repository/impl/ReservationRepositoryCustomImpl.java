package com.formation.orleanStay.repository.impl;

import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.Utilisateur;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import com.formation.orleanStay.repository.ReservationRepository;
import com.formation.orleanStay.repository.ReservationRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> findReservationsByCriteria(ReservationResearchRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = cb.createQuery(Reservation.class);
        Root<Reservation> reservation = query.from(Reservation.class);
        Join<Reservation, Appartment> appartment = reservation.join("appartment");
        Join<Appartment, Utilisateur> owner = appartment.join("owner");

        List<Predicate> predicates = new ArrayList<>();

        // Add conditions based on request
        if (request.getOwnerId() != null) {
            predicates.add(cb.equal(owner.get("id"), request.getOwnerId()));
        }

        if (request.getAppartmentId() != null) {
            predicates.add(cb.equal(appartment.get("id"), request.getAppartmentId()));
        }

        if (request.getYear() != null) {
            if (request.getMonth() != null) {
                LocalDateTime startDate = LocalDateTime.of(request.getYear(), request.getMonth() + 1, 1, 0, 0);
                LocalDateTime endDate = startDate.plusMonths(1);
                predicates.add(cb.between(reservation.get("checkinDate"), startDate, endDate));
            } else {
                LocalDateTime startDate = LocalDateTime.of(request.getYear(), 1, 1, 0, 0);
                LocalDateTime endDate = LocalDateTime.of(request.getYear(), 12, 31, 23, 59);
                predicates.add(cb.between(reservation.get("checkinDate"), startDate, endDate));
            }
        }

        if (request.getState() != null) {
            if ("accepted".equals(request.getState())) {
                predicates.add(cb.isTrue(reservation.get("accepted")));
            } else if ("notAccepted".equals(request.getState())) {
                predicates.add(cb.isFalse(reservation.get("accepted")));
            }
        }

        if (Boolean.TRUE.equals(request.getNotClosed())) {
            predicates.add(cb.greaterThan(reservation.get("checkoutDate"), LocalDateTime.now()));
        }

        // Combine all predicates
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
