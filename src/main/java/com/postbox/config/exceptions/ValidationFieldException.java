package com.postbox.config.exceptions;

public class ValidationFieldException extends RuntimeException {

    private final Class object;
    private final String field;
    private final Object value;

    /**
     * Constructs a ValidationFieldException, saving a reference to error object, field, value and error message.
     *
     * @param object the invalid object.
     * @param field the invalid field.
     * @param value the invalid value.
     * @param message the detail message.
     */
    public ValidationFieldException(Class object,  String field, Object value, String message) {
        super(message);
        this.object = object;
        this.field = field;
        this.value = value;
    }

    public Class getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
