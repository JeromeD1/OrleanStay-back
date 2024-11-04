package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.cloudinary.CloudinaryService;
import com.formation.orleanStay.mapper.TravelInfoMapper;
import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.DTO.TravelInfoDTO;
import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.entity.Photo;
import com.formation.orleanStay.models.entity.Reservation;
import com.formation.orleanStay.models.entity.TravelInfo;
import com.formation.orleanStay.models.request.TravelInfoListSaveRequest;
import com.formation.orleanStay.models.request.TravelInfoSaveRequest;
import com.formation.orleanStay.repository.TravelInfoRepository;
import com.formation.orleanStay.service.TravelInfoService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TravelInfoServiceImpl implements TravelInfoService {

    private final TravelInfoRepository travelInfoRepository;
    private final TravelInfoMapper travelInfoMapper;
    private final Findbyid findbyid;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<TravelInfoDTO> findByAppartmentId(Long appartmentId) {
        return travelInfoRepository.findByAppartment_Id(appartmentId)
                .stream()
                .map(travelInfoMapper::toTravelInfoDTO)
                .sorted(Comparator.comparing(TravelInfoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public List<TravelInfoDTO> findByReservationAndTravellerIds(Long reservationId, Long travellerId) {
        //récupération de la réservation
        final Reservation reservation = findbyid.findReservationById(reservationId);
        //verification que le traveller est le bon, sinon return null
        //idem si la date de sortie est déjà passée
        //idem si la reservation n'est pas acceptée
        final LocalDateTime now = LocalDateTime.now();
        if(!reservation.getTraveller().getId().equals(travellerId) ||
                now.isAfter(reservation.getCheckoutDate()) ||
                Boolean.FALSE.equals(reservation.getAccepted()) ||
                Boolean.TRUE.equals(reservation.getCancelled())) {
            return null;
        }

        return travelInfoRepository.findByAppartment_Id(reservation.getAppartmentId())
                .stream()
                .map(travelInfoMapper::toTravelInfoDTO)
                .sorted(Comparator.comparing(TravelInfoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public TravelInfoDTO create(TravelInfoSaveRequest travelInfoSaveRequest) {
     final TravelInfo travelInfoToCreate = travelInfoMapper.fromTravelInfoSaveRequest(travelInfoSaveRequest);
     final Appartment appartment = findbyid.findAppartmentById(travelInfoSaveRequest.getAppartmentId());
     travelInfoToCreate.setAppartment(appartment);
     TravelInfo savedTravelInfo = travelInfoRepository.save(travelInfoToCreate);

     return travelInfoMapper.toTravelInfoDTO(savedTravelInfo);
    }

    @Override
    public TravelInfoDTO update(Long id, TravelInfoSaveRequest travelInfoSaveRequest, String oldImgId) throws IOException {

        travelInfoSaveRequest.setId(id);
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(travelInfoSaveRequest.getAppartmentId());
        //Récupération de la travelInfo via son id
        final TravelInfo travelInfoToUpdate = findbyid.findTravelInfoById(id);
        //Mise à jour de l'appartement directement dans la travelInfo récupérée
        travelInfoToUpdate.setAppartment(appartment);
        //Modification via le mapper
        travelInfoMapper.overrideFromTravelInfoSaveRequest(travelInfoSaveRequest, travelInfoToUpdate);
        //sauvegarde du travelInfo modifié
        final TravelInfo savedTravelInfo = travelInfoRepository.save(travelInfoToUpdate);

        //suppression de l'ancienne photo si contentType = IMG_URL
        if (oldImgId != null && travelInfoSaveRequest.getContentType().name().equals("IMG_URL")) {
            cloudinaryService.deleteImage(oldImgId);
        }

        //renvoi du travelInfo modifiée au format DTO
        return travelInfoMapper.toTravelInfoDTO(savedTravelInfo);
    }

    @Override
    public List<TravelInfoDTO> updateOrder(Long appartmentId, TravelInfoListSaveRequest travelInfosToReorder) {
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(appartmentId);

        final List<TravelInfo> actualAppartmentTravelInfos = travelInfoRepository.findByAppartment_Id(appartmentId);

        if(actualAppartmentTravelInfos.size() != travelInfosToReorder.getTravelInfos().size()){
            throw new RuntimeException("Le nombre de d'éléments envoyés dans la requete doit être egal au nombre d'éléments dans les travelInfos de l'appartement.");
        }

        //Mise à jour des travelInfos de la requete + enregistrement en BDD
        final List<TravelInfo> travelInfos = travelInfosToReorder.getTravelInfos().stream()
                .map(travelInfoMapper::fromTravelInfoSaveRequest)
                .map(travelInfo -> {
                    travelInfo.setAppartment(appartment);
                    return travelInfo;
                })
                .toList();
        List<TravelInfo> updatedTravelInfos = travelInfos.stream().map(travelInfoRepository::save).toList();

        //Renvoi des travelInfos en DTO triées par positionOrder
        return updatedTravelInfos.stream()
                .map(travelInfoMapper::toTravelInfoDTO)
                .sorted(Comparator.comparing(TravelInfoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public List<TravelInfoDTO> delete(Long id, String oldImgId) throws IOException {
        TravelInfo travelInfoToDelete = findbyid.findTravelInfoById(id);

        //suppression de l'image de cloudinary si l'élément est une image
        if (oldImgId != null && travelInfoToDelete.getContentType().name().equals("IMG_URL")) {
            cloudinaryService.deleteImage(oldImgId);
        }

        final Integer deletedPositionOrder = travelInfoToDelete.getPositionOrder();
        final Long deletedTravelInfoAppartmentId = travelInfoToDelete.getAppartment().getId();
        travelInfoRepository.delete(travelInfoToDelete);

        //récupération de toutes les travelInfos de l'appartement pour mettre à jour l'ordre
        List<TravelInfo> travelInfos = travelInfoRepository.findByAppartment_Id(deletedTravelInfoAppartmentId);

        travelInfos.forEach(travelInfo -> {
                    if(travelInfo.getPositionOrder() > deletedPositionOrder) {
                        travelInfo.setPositionOrder(travelInfo.getPositionOrder() - 1);
                        travelInfoRepository.save(travelInfo);
                    }
                }
        );

        return travelInfos.stream()
                .map(travelInfoMapper::toTravelInfoDTO)
                .sorted(Comparator.comparing(TravelInfoDTO::getPositionOrder))
                .toList();
    }

}
