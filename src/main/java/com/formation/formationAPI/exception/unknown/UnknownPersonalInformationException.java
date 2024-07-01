package com.formation.formationAPI.exception.unknown;

public class UnknownPersonalInformationException extends RuntimeException{
    public static final String UNKNOWN_PERSONAL_INFORMATION_ID_MESSAGE = "PersonalInformation not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown personalInformation
     */
    public UnknownPersonalInformationException(Long id) {
        super(String.format(UNKNOWN_PERSONAL_INFORMATION_ID_MESSAGE, id));
    }
}
