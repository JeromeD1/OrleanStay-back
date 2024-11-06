package com.formation.orleanStay.exception.unknown;

public class UnknownRecoveryTokenException extends RuntimeException {
    public static final String UNKNOWN_RECOVERY_TOKEN_MESSAGE = "Le token fournit n'existe pas.";

    public UnknownRecoveryTokenException() {
        super(String.format(UNKNOWN_RECOVERY_TOKEN_MESSAGE));
    }
}
