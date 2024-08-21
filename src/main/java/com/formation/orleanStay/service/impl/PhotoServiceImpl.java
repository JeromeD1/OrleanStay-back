package com.formation.orleanStay.service.impl;

import com.cloudinary.Cloudinary;
import com.formation.orleanStay.cloudinary.CloudinaryService;
import com.formation.orleanStay.mapper.PhotoMapper;
import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.entity.Appartment;
import com.formation.orleanStay.models.entity.Photo;
import com.formation.orleanStay.models.request.PhotoListSaveRequest;
import com.formation.orleanStay.models.request.PhotoSaveRequest;
import com.formation.orleanStay.repository.PhotoRepository;
import com.formation.orleanStay.service.PhotoService;
import com.formation.orleanStay.utils.Findbyid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    final private PhotoRepository photoRepository;
    final private PhotoMapper photoMapper;
    final private Findbyid findbyid;
    final private CloudinaryService cloudinaryService;



    @Override
    public List<PhotoDTO> findAll() {
        final List<Photo> photos = photoRepository.findAll();
        return photos.stream()
                .map(photoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public PhotoDTO findDTOById(Long id) {
        final Photo photo = findbyid.findPhotoById(id);
        return photoMapper.toPhotoDTO(photo);
    }

    @Override
    public List<PhotoDTO> create(PhotoSaveRequest photoSaveRequest) {
        //Récupération de toutes les photos de l'appartement
        final Appartment appartment = findbyid.findAppartmentById(photoSaveRequest.getAppartmentId());
        // Récupération des photos de l'appartement
        final List<Photo> appartmentPhotos = appartment.getPhotos();
        //Mise à jour de positionOrder des photos de l'appartement
        for (Photo photo : appartmentPhotos){
            if(photo.getPositionOrder() >= photoSaveRequest.getPositionOrder()) {
                photo.setPositionOrder(photo.getPositionOrder() + 1);
            }
            photoRepository.save(photo);
        }

        // Création de la photo sans l'appartement pour obtenir un ID
        final Photo photoToSave = photoMapper.fromPhotoSaveRequest(photoSaveRequest);
        final Photo savedPhoto = photoRepository.save(photoToSave);
        // Mise à jour de la photo avec la référence à l'appartement
        savedPhoto.setAppartment(appartment);
        // Sauvegarde de la photo mise à jour
        final Photo updatedPhoto = photoRepository.save(savedPhoto);

        //Ajout de la photo dans la liste à retourner
        appartmentPhotos.add(updatedPhoto);

        return appartmentPhotos.stream()
                .map(photoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }


    @Override
    public PhotoDTO update(Long id, String oldImgId, PhotoSaveRequest photoSaveRequest) throws IOException {
        photoSaveRequest.setId(id);
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(photoSaveRequest.getAppartmentId());
        //Récupération de la photo via son id
        final Photo photoToUpdate = findbyid.findPhotoById(id);
        //Mise à jour de l'appartement directement dans la photo récupérée car compliqué via le mapper car interface
        photoToUpdate.setAppartment(appartment);
        //Modification de imgUrl via le mapper
        photoMapper.overrideFromPhotoSaveRequest(photoSaveRequest, photoToUpdate);
        //sauvegarde de la photo modifiée
        final Photo savedPhoto = photoRepository.save(photoToUpdate);

        //suppression de l'ancienne photo
        cloudinaryService.deleteImage(oldImgId);

        //renvoi de la photo modifiée au format DTO
        return photoMapper.toPhotoDTO(savedPhoto);
    }

    @Override
    public List<PhotoDTO> updateOrder(Long appartmentId, PhotoListSaveRequest photosToReorder){
        //Récupération de l'appartement suivant sa référence (id)
        final Appartment appartment = findbyid.findAppartmentById(appartmentId);

        //condition sur la longueur du tableau envoyé --> = au nombre de photos de l'appartement -> sinon ERREUR
        final List<Photo> actualAppartmentPhotos = photoRepository.findByAppartment_Id(appartmentId);

        if(actualAppartmentPhotos.size() != photosToReorder.getPhotos().size()){
            throw new RuntimeException("Le nombre de photos envoyées dans la requete doit être egal au nombre de photos de l'appartement.");
        }

        //Mise à jour des photos de la requete + enregistrement en BDD
        final List<Photo> photos = photosToReorder.getPhotos().stream()
                .map(photoMapper::fromPhotoSaveRequest)
                .map(photo -> {
                    photo.setAppartment(appartment);
                    return photo;
                })
                .toList();
        List<Photo> updatedPhotos = photos.stream().map(photoRepository::save).toList();

        //Renvoi des photos en DTO triées par positionOrder
        return updatedPhotos.stream()
                .map(photoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }

    @Override
    public List<PhotoDTO> delete(Long id, String imgId) throws IOException {
        System.out.println("imgId: " + imgId);
        cloudinaryService.deleteImage(imgId);
        System.out.println("passé etape cloudinary");
        final Photo photoToDelete = findbyid.findPhotoById(id);
        final Integer deletedPositionOrder = photoToDelete.getPositionOrder();
        final Long deletedPhotoId = photoToDelete.getAppartment().getId();
        photoRepository.delete(photoToDelete);

        //récupération de toutes les photos de l'appartement pour mettre à jour l'ordre
        List<Photo> photos = photoRepository.findByAppartment_Id(deletedPhotoId);

        photos.forEach(photo -> {
            if(photo.getPositionOrder() > deletedPositionOrder) {
                photo.setPositionOrder(photo.getPositionOrder() - 1);
                photoRepository.save(photo);
            }
                }
        );

        System.out.println(photos);
        System.out.println(photos.toString());
        System.out.println(photos.stream().map(photoMapper::toPhotoDTO).toList());
        System.out.println(photos.stream().map(photoMapper::toPhotoDTO).toList().toString());
        return photos.stream()
                .map(photoMapper::toPhotoDTO)
                .sorted(Comparator.comparing(PhotoDTO::getPositionOrder))
                .toList();
    }

}
