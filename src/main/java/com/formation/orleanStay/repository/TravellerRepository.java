package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, Long> {
}
