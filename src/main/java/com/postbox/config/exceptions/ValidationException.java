package com.postbox.config.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ValidationFieldException> exceptionFields;

    /**
     * Constructs a ValidationFieldException, saving a reference to error message and no validation field error.
     *
     * @param message the detail message.
     */
    public ValidationException(String message) {
        super(message);
        this.exceptionFields = new ArrayList<>();
    }

    /**
     * Constructs a ValidationFieldException, saving a reference to error message and validation error for only one field.
     *
     * @param message the detail message.
     * @param exceptionField the error for one field.
     */
    public ValidationException(String message, ValidationFieldException exceptionField) {
        super(message);
        this.exceptionFields = Arrays.asList(exceptionField);
    }

    /**
     * Constructs a ValidationFieldException, saving a reference to error message and validation error for each field.
     *
     * @param message the detail message.
     * @param exceptionFields the list of error for each field.
     */
    public ValidationException(String message, List<ValidationFieldException> exceptionFields) {
        super(message);
        this.exceptionFields = exceptionFields;
    }

    public List<ValidationFieldException> getExceptionFields() {
        return exceptionFields;
    }
}
