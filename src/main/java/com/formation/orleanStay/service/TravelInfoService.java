package com.formation.orleanStay.service;

import com.formation.orleanStay.models.DTO.TravelInfoDTO;
import com.formation.orleanStay.models.request.TravelInfoListSaveRequest;
import com.formation.orleanStay.models.request.TravelInfoSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public interface TravelInfoService {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<TravelInfoDTO> findByAppartmentId(Long appartmentId);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<TravelInfoDTO> findByReservationAndTravellerIds(Long reservationId, Long travellerId);

    @Transactional(propagation = Propagation.REQUIRED)
    TravelInfoDTO create(TravelInfoSaveRequest travelInfoSaveRequest);

    @Transactional(propagation = Propagation.REQUIRED)
    TravelInfoDTO update(Long id, TravelInfoSaveRequest travelInfoSaveRequest, String oldImgId) throws IOException;

    @Transactional(propagation = Propagation.REQUIRED)
    List<TravelInfoDTO> updateOrder(Long appartmentId, TravelInfoListSaveRequest travelInfosToReorder);

    @Transactional(propagation = Propagation.REQUIRED)
    List<TravelInfoDTO> delete(Long id, String oldImgId) throws IOException;
}
