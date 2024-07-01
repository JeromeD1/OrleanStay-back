package com.formation.orleanStay.exception.unknown;

public class UnknownTravellerException extends RuntimeException{
    public static final String UNKNOWN_TRAVELLER_ID_MESSAGE = "Traveller not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown traveller
     */
    public UnknownTravellerException(Long id) {
        super(String.format(UNKNOWN_TRAVELLER_ID_MESSAGE, id));
    }
}
