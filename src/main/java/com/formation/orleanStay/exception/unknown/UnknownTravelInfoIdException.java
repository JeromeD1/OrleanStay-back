package com.formation.orleanStay.exception.unknown;

public class UnknownTravelInfoIdException extends RuntimeException{
    public static final String UNKNOWN_TRAVEL_INFO_ID_MESSAGE = "Travel info not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the Travel Info
     */
    public UnknownTravelInfoIdException(Long id) {
        super(String.format(UNKNOWN_TRAVEL_INFO_ID_MESSAGE, id));
    }
}
