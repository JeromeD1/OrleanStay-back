package com.formation.formationAPI.exception.unknown;

public class UnknownReservationChatIdException extends RuntimeException{
    public static final String UNKNOWN_RESERVATION_CHAT_ID_MESSAGE = "ReservationChat not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown reservationChat
     */
    public UnknownReservationChatIdException(Long id) {
        super(String.format(UNKNOWN_RESERVATION_CHAT_ID_MESSAGE, id));
    }
}
