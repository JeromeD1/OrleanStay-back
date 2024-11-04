package com.formation.orleanStay.exception.forbidden;

public class ForbiddenRoleChangeException extends RuntimeException {
    public static final String MESSAGE = "L'utilisateur d'id %s possède des appartements et ne peut pas être rétrogradé au role de USER";

    /**
     * Constructor
     *
     * @param id of the user
     */
    public ForbiddenRoleChangeException(Long id) {
        super(String.format(MESSAGE, id));
    }

}
