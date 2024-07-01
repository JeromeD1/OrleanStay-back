package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.ReservationChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationChatRepository extends JpaRepository<ReservationChat, Long> {
    List<ReservationChat> findByReservation_Id(Long reservationId);
}
