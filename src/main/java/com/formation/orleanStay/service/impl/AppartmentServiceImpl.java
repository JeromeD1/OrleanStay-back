package com.formation.orleanStay.service.impl;

import com.formation.orleanStay.mapper.AppartmentMapper;
import com.formation.orleanStay.models.DTO.AppartmentDTO;
import com.formation.orleanStay.models.DTO.AppartmentNameAndOwnerDTO;
import com.formation.orleanStay.models.entity.*;
import com.formation.orleanStay.models.request.AppartmentSaveRequest;
import com.formation.orleanStay.repository.AppartmentRepository;
import com.formation.orleanStay.service.AppartmentService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class AppartmentServiceImpl implements AppartmentService {

    private final AppartmentRepository appartmentRepository;
    private  final AppartmentMapper appartmentMapper;
    final private Findbyid findbyid;
    public AppartmentServiceImpl(AppartmentRepository appartmentRepository, AppartmentMapper appartmentMapper, Findbyid findbyid) {
        this.appartmentRepository = appartmentRepository;
        this.appartmentMapper = appartmentMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<AppartmentDTO> findAll() {
        final List<Appartment> appartments = appartmentRepository.findAll();
        return appartments.stream()
                .map(appartment -> {
                    final List<Info> sortedInfos = appartment.getInfos().stream().sorted(Comparator.comparing(Info::getPositionOrder)).toList();
                    final List<Photo> sortedPhotos = appartment.getPhotos().stream().sorted(Comparator.comparing(Photo::getPositionOrder)).toList();
                    appartment.setInfos(sortedInfos);
                    appartment.setPhotos(sortedPhotos);
                    return appartment;
                })
                .map(appartmentMapper::toAppartmentDTO)
                .toList();
    }

    @Override
    public List<AppartmentNameAndOwnerDTO> findAllNamesAndOwners() {
        final List<Appartment> appartments = appartmentRepository.findAll();
        return appartments.stream()
                .map(appartmentMapper::toAppartmentNameAndOwnerDTO)
                .toList();
    }

    @Override
    public List<AppartmentDTO> findAllActive() {
        final List<Appartment> appartments = appartmentRepository.findByActiveTrue();
        return appartments.stream()
                .map(appartment -> {
                    final List<Info> sortedInfos = appartment.getInfos().stream().sorted(Comparator.comparing(Info::getPositionOrder)).toList();
                    final List<Photo> sortedPhotos = appartment.getPhotos().stream().sorted(Comparator.comparing(Photo::getPositionOrder)).toList();
                    appartment.setInfos(sortedInfos);
                    appartment.setPhotos(sortedPhotos);
                    return appartment;
                })
                .map(appartmentMapper::toAppartmentDTO)
                .toList();
    }

    @Override
    public AppartmentDTO findDTOById(Long id){
        Appartment appartment = findbyid.findAppartmentById(id);
        final List<Info> sortedInfos = appartment.getInfos().stream().sorted(Comparator.comparing(Info::getPositionOrder)).toList();
        final List<Photo> sortedPhotos = appartment.getPhotos().stream().sorted(Comparator.comparing(Photo::getPositionOrder)).toList();
        appartment.setInfos(sortedInfos);
        appartment.setPhotos(sortedPhotos);
        return appartmentMapper.toAppartmentDTO(appartment);
    }

    @Override
    public List<AppartmentDTO> findByOwnerId(Long ownerId){
        final List<Appartment> appartments = appartmentRepository.findByOwner_Id(ownerId);
        return appartments.stream()
                .map(appartment -> {
                    final List<Info> sortedInfos = appartment.getInfos().stream().sorted(Comparator.comparing(Info::getPositionOrder)).toList();
                    final List<Photo> sortedPhotos = appartment.getPhotos().stream().sorted(Comparator.comparing(Photo::getPositionOrder)).toList();
                    appartment.setInfos(sortedInfos);
                    appartment.setPhotos(sortedPhotos);
                    return appartment;
                })
                .map(appartmentMapper::toAppartmentDTO)
                .toList();
    }

    @Override
    public AppartmentDTO create(AppartmentSaveRequest appartmentSaveRequest) {
        final Discount discount = findbyid.findDiscountById(appartmentSaveRequest.getDiscountId());
        appartmentSaveRequest.setDiscounts(discount);

        final Utilisateur owner = findbyid.findUtilisateurById(appartmentSaveRequest.getOwnerId());
        appartmentSaveRequest.setOwner(owner);


        final Appartment appartment = appartmentMapper.fromAppartmentSaveRequest(appartmentSaveRequest);

        final Appartment savedAppartment = appartmentRepository.save(appartment);

        return appartmentMapper.toAppartmentDTO(savedAppartment);
    }


    @Override
    public AppartmentDTO update(Long id, AppartmentSaveRequest appartmentSaveRequest) {
        if(appartmentSaveRequest.getId() == null){appartmentSaveRequest.setId(id);}

        final Discount discount = findbyid.findDiscountById(appartmentSaveRequest.getDiscountId());
        appartmentSaveRequest.setDiscounts(discount);

        final Utilisateur owner = findbyid.findUtilisateurById(appartmentSaveRequest.getOwnerId());
        appartmentSaveRequest.setOwner(owner);

        final Appartment appartmentToUpdate = findbyid.findAppartmentById(id);

        if(appartmentSaveRequest.getActive() == null){
            appartmentSaveRequest.setActive(appartmentToUpdate.getActive());
        }

        appartmentMapper.overrideFromAppartmentSaveRequest(appartmentSaveRequest, appartmentToUpdate);

        final Appartment savedAppartment = appartmentRepository.save(appartmentToUpdate);

        //réarangement de l'ordre des photos et des infos
        final List<Info> sortedInfos = savedAppartment.getInfos().stream().sorted(Comparator.comparing(Info::getPositionOrder)).toList();
        final List<Photo> sortedPhotos = savedAppartment.getPhotos().stream().sorted(Comparator.comparing(Photo::getPositionOrder)).toList();
        savedAppartment.setInfos(sortedInfos);
        savedAppartment.setPhotos(sortedPhotos);

        return appartmentMapper.toAppartmentDTO(savedAppartment);
    }

    @Override
    public void delete(Long id){
        final Appartment appartmentToDelete = findbyid.findAppartmentById(id);
        appartmentRepository.delete(appartmentToDelete);
    }

}
