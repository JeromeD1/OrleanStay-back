package com.formation.orleanStay.exception.unknown;

public class UnknownReservationException extends RuntimeException{
    public static final String UNKNOWN_RESERVATION_ID_MESSAGE = "Reservation not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown reservation
     */
    public UnknownReservationException(Long id) {
        super(String.format(UNKNOWN_RESERVATION_ID_MESSAGE, id));
    }
}
