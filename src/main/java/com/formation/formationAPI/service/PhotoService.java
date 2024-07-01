package com.formation.formationAPI.service;

import com.formation.formationAPI.models.DTO.PhotoDTO;
import com.formation.formationAPI.models.request.PhotoListSaveRequest;
import com.formation.formationAPI.models.request.PhotoSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PhotoService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<PhotoDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    PhotoDTO findDTOById(Long id);

//    @Transactional(propagation = Propagation.REQUIRED)
//    PhotoDTO create(PhotoSaveRequest photoSaveRequest);
//
//    @Transactional(propagation = Propagation.REQUIRED)
//    PhotoDTO update(Long id, PhotoSaveRequest photoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    List<PhotoDTO> create(PhotoSaveRequest photoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    PhotoDTO update(Long id, PhotoSaveRequest photoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    List<PhotoDTO> updateOrder(Long id, PhotoListSaveRequest photosToReorder);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(Long id);
}
