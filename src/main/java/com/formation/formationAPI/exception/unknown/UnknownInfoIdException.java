package com.formation.formationAPI.exception.unknown;

public class UnknownInfoIdException extends RuntimeException {
    public static final String UNKNOWN_INFOID_MESSAGE = "Info not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown info
     */
    public UnknownInfoIdException(Long id) {
        super(String.format(UNKNOWN_INFOID_MESSAGE, id));
    }
}
