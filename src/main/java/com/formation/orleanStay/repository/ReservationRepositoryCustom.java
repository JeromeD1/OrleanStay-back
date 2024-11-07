package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.request.ReservationResearchRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepositoryCustom {
    List<Reservation> findReservationsByCriteria(ReservationResearchRequest request);
}
