package com.formation.orleanStay.exception.unknown;

public class UnknownAppartmentIdException extends RuntimeException {

    public static final String UNKNOWN_APPARTMENTID_MESSAGE = "Appartment not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown appartment
     */
    public UnknownAppartmentIdException(Long id) {
        super(String.format(UNKNOWN_APPARTMENTID_MESSAGE, id));
    }

}
