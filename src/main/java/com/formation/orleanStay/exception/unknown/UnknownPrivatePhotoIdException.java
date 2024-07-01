package com.formation.orleanStay.exception.unknown;

public class UnknownPrivatePhotoIdException extends RuntimeException{
    public static final String UNKNOWN_PRIVATE_PHOTO_ID_MESSAGE = "Private photo not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown photo
     */
    public UnknownPrivatePhotoIdException(Long id) {
        super(String.format(UNKNOWN_PRIVATE_PHOTO_ID_MESSAGE, id));
    }
}
