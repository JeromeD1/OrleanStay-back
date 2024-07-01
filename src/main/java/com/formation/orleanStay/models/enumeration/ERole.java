package com.formation.orleanStay.models.enumeration;

public enum ERole {
    USER,
    OWNER,
    ADMIN;

    public static class Constants {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String OWNER = "OWNER";

//        private Constants() {
//        }
    }
}
