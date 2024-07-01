package com.formation.formationAPI.exception.unknown;

public class UnknownDiscountIdException extends RuntimeException{
    public static final String UNKNOWN_DISCOUNTID_MESSAGE = "Discount not found with id : %s";

    /**
     * Constructor
     *
     * @param id of the unknown discount
     */
    public UnknownDiscountIdException(Long id) {
        super(String.format(UNKNOWN_DISCOUNTID_MESSAGE, id));
    }
}
