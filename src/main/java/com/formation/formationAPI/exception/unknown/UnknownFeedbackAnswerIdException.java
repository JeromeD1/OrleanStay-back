package com.formation.formationAPI.exception.unknown;

public class UnknownFeedbackAnswerIdException extends RuntimeException{
    public static final String UNKNOWN_FEEDBACK_ANSWER_ID_MESSAGE = "FeedbackAnswer not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown feedback
     */
    public UnknownFeedbackAnswerIdException(Long id) {
        super(String.format(UNKNOWN_FEEDBACK_ANSWER_ID_MESSAGE, id));
    }
}
