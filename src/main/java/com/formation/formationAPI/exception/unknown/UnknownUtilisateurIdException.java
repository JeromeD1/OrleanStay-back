package com.formation.formationAPI.exception.unknown;

public class UnknownUtilisateurIdException extends RuntimeException{
    public static final String UNKNOWN_UTILISATEURID_MESSAGE = "User not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown user
     */
    public UnknownUtilisateurIdException(Long id) {
        super(String.format(UNKNOWN_UTILISATEURID_MESSAGE, id));
    }

}
