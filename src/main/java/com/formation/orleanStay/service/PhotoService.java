package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.request.PhotoListSaveRequest;
import com.formation.orleanStay.models.request.PhotoSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public interface PhotoService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<PhotoDTO> findAll();

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    PhotoDTO findDTOById(Long id);

    @Transactional(propagation = Propagation.REQUIRED)
    List<PhotoDTO> create(PhotoSaveRequest photoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    PhotoDTO update(Long id, String imgId, PhotoSaveRequest photoSaveRequest) throws IOException;

    @Transactional(propagation = Propagation.REQUIRED)
    List<PhotoDTO> updateOrder(Long id, PhotoListSaveRequest photosToReorder);

    @Transactional(propagation = Propagation.REQUIRED)
    List<PhotoDTO> delete(Long id, String oldImgId) throws IOException;
}
