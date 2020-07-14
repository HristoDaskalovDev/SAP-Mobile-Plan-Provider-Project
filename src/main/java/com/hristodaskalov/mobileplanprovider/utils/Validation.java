package com.hristodaskalov.mobileplanprovider.utils;

import com.hristodaskalov.mobileplanprovider.exception.InvalidInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static void fieldIsNotEmptyAndHasValidLength(String value, int maxLength, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(
                    String.format("Field %s cannot be empty", field)
            );
        }
        if (value.length() > maxLength) {
            throw new InvalidInputException(
                    String.format("Field %s cannot be longer than %d characters.", field, maxLength)
            );
        }
    }

    public static void validateFieldByRegex(String field, String regex, String fieldName) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        if (!matcher.matches()) {
            throw new InvalidInputException(String.format("[%s] is not a valid %s.", field, fieldName));
        }
    }
}
