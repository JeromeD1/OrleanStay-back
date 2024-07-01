package com.formation.formationAPI.mapper;

import com.formation.formationAPI.models.DTO.PhotoDTO;
import com.formation.formationAPI.models.entity.Info;
import com.formation.formationAPI.models.entity.Photo;
import com.formation.formationAPI.models.request.PhotoSaveRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PhotoMapper {
    /**
     * Mapping photoId to the id of {@link Photo} entity
     *
     * @param photoId to map
     * @return the {@link Photo} with the id of the photoId value
     */
    default Photo fromPhotoId(Long photoId) {
        if(photoId == null) {
            return null;
        }

        final Photo photo = new Photo();
        photo.setId(photoId);
        return photo;
    }


    /**
     * Mapping {@link Photo} to {@link PhotoDTO}
     *
     * @param photo to map
     * @return the {@link PhotoDTO} mapped from {@link Photo}
     */
    PhotoDTO toPhotoDTO(Photo photo);



    /**
     * Mapping {@link PhotoSaveRequest} to {@link Photo}
     *
     * @param photoSaveRequest to map
     * @return the {@link Photo} mapped from {@link PhotoSaveRequest}
     */
    Photo fromPhotoSaveRequest(PhotoSaveRequest photoSaveRequest);


    /**
     * Override {@link Photo} with {@link PhotoSaveRequest}
     *
     * @param saveRequest the {@link PhotoSaveRequest} used to override {@link Photo}
     * @param photo {@link Photo} to be overwritten
     */
    @Mapping(source = "saveRequest.imgUrl", target = "imgUrl")
    @Mapping(source = "saveRequest.positionOrder", target = "positionOrder")
    void overrideFromPhotoSaveRequest(PhotoSaveRequest saveRequest, @MappingTarget Photo photo);
}
