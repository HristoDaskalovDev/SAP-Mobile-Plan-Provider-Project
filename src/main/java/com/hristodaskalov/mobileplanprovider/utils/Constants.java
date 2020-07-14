package com.hristodaskalov.mobileplanprovider.utils;

public class Constants {

    // field length constants
    public static final int NAME_MAX_LENGTH = 128;
    public static final int EMAIL_MAX_LENGTH = 128;
    public static final int PASSWORD_MAX_LENGTH = 128;
    public static final int NATIONAL_ID_MAX_LENGTH = 16;
    public static final int PHONE_MAX_LENGTH = 16;
    public static final int ADDRESS_MAX_LENGTH = 128;

    // role constants
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // regex expression constants
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String PHONE_NUMBER_REGEX = "^\\d{10}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    public static final String NATIONAL_ID_REGEX = "^\\d{10}$";

    // date pattern
    public static final String DATE_FORMAT = "ddMMyyyy";
}
