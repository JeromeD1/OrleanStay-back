package com.formation.formationAPI.repository;

import com.formation.formationAPI.models.entity.Appartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppartmentRepository extends JpaRepository<Appartment, Long> {

    //Récupération des appartements actifs suivant convention de nommage
    List<Appartment> findByActiveTrue();

    List<Appartment> findByOwner_Id(Long ownerId);
}
