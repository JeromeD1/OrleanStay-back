package com.formation.orleanStay.repository;

import com.formation.orleanStay.models.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByAppartment_Id(Long id);
}
