package com.formation.formationAPI.service.impl;

import com.formation.formationAPI.mapper.PrivatePhotoMapper;
import com.formation.formationAPI.models.DTO.PhotoDTO;
import com.formation.formationAPI.models.entity.Appartment;
import com.formation.formationAPI.models.entity.PrivatePhoto;
import com.formation.formationAPI.models.request.PhotoListSaveRequest;
import com.formation.formationAPI.models.request.PhotoSaveRequest;
import com.formation.formationAPI.repository.PrivatePhotoRepository;
import com.formation.formationAPI.service.PrivatePhotoService;
import com.formation.formationAPI.utils.Findbyid;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PrivatePhotoServiceImpl implements PrivatePhotoService {
    final private PrivatePhotoRepository privatePhotoRepository;
    final private PrivatePhotoMapper privatePhotoMapper;
    final private Findbyid findbyid;

    public PrivatePhotoServiceImpl(PrivatePhotoRepository privatePhotoRepository, PrivatePhotoMapper privatePhotoMapper, Findbyid findbyid) {
        this.privatePhotoRepository = privatePhotoRepository;
        this.privatePhotoMapper = privatePhotoMapper;
        this.findbyid = findbyid;
    }

    @Override
    public List<PhotoDTO> findByAppartmentId(Long appartmentId) {
        final List<PrivatePhoto> photos = privatePhotoRepository.findByAppartment_Id(appartmentId);
        return photos.stream()
                .map(privatePhotoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public List<PhotoDTO> create(PhotoSaveRequest photoSaveRequest) {
        //Récupération de toutes les photos de l'appartement
        final Appartment appartment = findbyid.findAppartmentById(photoSaveRequest.getAppartmentId());
        // Récupération des photos de l'appartement
        final List<PrivatePhoto> appartmentPhotos = privatePhotoRepository.findByAppartment_Id(photoSaveRequest.getAppartmentId());
        //Mise à jour de positionOrder des photos de l'appartement
        for (PrivatePhoto photo : appartmentPhotos){
            if(photo.getPositionOrder() >= photoSaveRequest.getPositionOrder()) {
                photo.setPositionOrder(photo.getPositionOrder() + 1);
            }
            privatePhotoRepository.save(photo);
        }

        // Création de la photo sans l'appartement pour obtenir un ID
        final PrivatePhoto photoToSave = privatePhotoMapper.fromPhotoSaveRequest(photoSaveRequest);
        final PrivatePhoto savedPhoto = privatePhotoRepository.save(photoToSave);
        // Mise à jour de la photo avec la référence à l'appartement
        savedPhoto.setAppartment(appartment);
        // Sauvegarde de la photo mise à jour
        final PrivatePhoto updatedPhoto = privatePhotoRepository.save(savedPhoto);

        //Ajout de la photo dans la liste à retourner
        appartmentPhotos.add(updatedPhoto);

        return appartmentPhotos.stream()
                .map(privatePhotoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }


    @Override
    public PhotoDTO update(Long id, PhotoSaveRequest photoSaveRequest){
        photoSaveRequest.setId(id);
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(photoSaveRequest.getAppartmentId());
        //Récupération de la photo via son id
        final PrivatePhoto photoToUpdate = findbyid.findPrivatePhotoById(id);
        //Mise à jour de l'appartement directement dans la photo récupérée car compliqué via le mapper car interface
        photoToUpdate.setAppartment(appartment);
        //Modification de imgUrl via le mapper
        privatePhotoMapper.overrideFromPhotoSaveRequest(photoSaveRequest, photoToUpdate);
        //sauvegarde de la photo modifiée
        final PrivatePhoto savedPhoto = privatePhotoRepository.save(photoToUpdate);
        //renvoi de la photo modifiée au format DTO
        return privatePhotoMapper.toPhotoDTO(savedPhoto);
    }

    @Override
    public List<PhotoDTO> updateOrder(Long appartmentId, PhotoListSaveRequest photosToReorder){
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(appartmentId);

        //condition sur la longueur du tableau envoyé --> = au nombre de photos de l'appartement -> sinon ERREUR
        final List<PrivatePhoto> actualAppartmentPhotos = privatePhotoRepository.findByAppartment_Id(appartmentId);

        if(actualAppartmentPhotos.size() != photosToReorder.getPhotos().size()){
            throw new RuntimeException("Le nombre de photos envoyées dans la requete doit être egal au nombre de private photos de l'appartement.");
        }

        //Mise à jour des photos de la requete + enregistrement en BDD
        final List<PrivatePhoto> photos = photosToReorder.getPhotos().stream()
                .map(privatePhotoMapper::fromPhotoSaveRequest)
                .map(photo -> {
                    photo.setAppartment(appartment);
                    return photo;
                })
                .toList();
        List<PrivatePhoto> updatedPhotos = photos.stream().map(privatePhotoRepository::save).toList();

        //Renvoi des photos en DTO triées par positionOrder
        return updatedPhotos.stream()
                .map(privatePhotoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public void delete(Long id) {
        final PrivatePhoto photoToDelete = findbyid.findPrivatePhotoById(id);
        privatePhotoRepository.delete(photoToDelete);
    }


}
