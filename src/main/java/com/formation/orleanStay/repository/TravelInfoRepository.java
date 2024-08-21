package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.TravelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelInfoRepository extends JpaRepository<TravelInfo, Long> {
    List<TravelInfo> findByAppartment_Id(Long appartmentId);
}
