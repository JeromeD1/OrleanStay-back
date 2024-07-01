package com.formation.orleanStay.exception.unknown;

public class UnknownPhotoIdException extends RuntimeException{
    public static final String UNKNOWN_PHOTOID_MESSAGE = "Photo not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown photo
     */
    public UnknownPhotoIdException(Long id) {
        super(String.format(UNKNOWN_PHOTOID_MESSAGE, id));
    }
}
