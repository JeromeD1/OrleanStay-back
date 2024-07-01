package com.formation.formationAPI.repository;

import com.formation.formationAPI.models.entity.PrivatePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivatePhotoRepository extends JpaRepository<PrivatePhoto, Long> {
    List<PrivatePhoto> findByAppartment_Id(Long appartmentId);
}
