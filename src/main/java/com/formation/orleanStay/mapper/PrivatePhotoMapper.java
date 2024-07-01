package com.formation.orleanStay.mapper;

import com.formation.orleanStay.models.DTO.PhotoDTO;
import com.formation.orleanStay.models.entity.Photo;
import com.formation.orleanStay.models.entity.PrivatePhoto;
import com.formation.orleanStay.models.request.PhotoSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PrivatePhotoMapper {
    /**
     * Mapping photoId to the id of {@link PrivatePhoto} entity
     *
     * @param photoId to map
     * @return the {@link Photo} with the id of the privatePhotoId value
     */
    default PrivatePhoto fromPrivatePhotoId(Long photoId) {
        if(photoId == null) {
            return null;
        }

        final PrivatePhoto photo = new PrivatePhoto();
        photo.setId(photoId);
        return photo;
    }


    /**
     * Mapping {@link PrivatePhoto} to {@link PhotoDTO}
     *
     * @param photo to map
     * @return the {@link PhotoDTO} mapped from {@link PrivatePhoto}
     */
    PhotoDTO toPhotoDTO(PrivatePhoto photo);



    /**
     * Mapping {@link PhotoSaveRequest} to {@link PrivatePhoto}
     *
     * @param photoSaveRequest to map
     * @return the {@link PrivatePhoto} mapped from {@link PhotoSaveRequest}
     */
    PrivatePhoto fromPhotoSaveRequest(PhotoSaveRequest photoSaveRequest);


    /**
     * Override {@link PrivatePhoto} with {@link PhotoSaveRequest}
     *
     * @param saveRequest the {@link PhotoSaveRequest} used to override {@link PrivatePhoto}
     * @param photo {@link PrivatePhoto} to be overwritten
     */
    @Mapping(source = "saveRequest.imgUrl", target = "imgUrl")
    @Mapping(source = "saveRequest.positionOrder", target = "positionOrder")
    void overrideFromPhotoSaveRequest(PhotoSaveRequest saveRequest, @MappingTarget PrivatePhoto photo);
}
