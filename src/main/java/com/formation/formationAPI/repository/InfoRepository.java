package com.formation.formationAPI.repository;

import com.formation.formationAPI.models.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
    List<Info> findByAppartment_Id(Long id);
}
