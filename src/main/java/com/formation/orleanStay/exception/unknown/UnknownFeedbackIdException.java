package com.formation.orleanStay.exception.unknown;

public class UnknownFeedbackIdException extends RuntimeException{
    public static final String UNKNOWN_FEEDBACK_ID_MESSAGE = "Feedback not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown feedback
     */
    public UnknownFeedbackIdException(Long id) {
        super(String.format(UNKNOWN_FEEDBACK_ID_MESSAGE, id));
    }
}
