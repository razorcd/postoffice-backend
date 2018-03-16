package com.postbox.config.errorhandling;

public class CustomValidationErrorDTO {
    private Class object;
    private String field;
    private Object rejectedValue;
    private String message;

    CustomValidationErrorDTO(Class object, String message) {
        this.object = object;
        this.message = message;
    }

    CustomValidationErrorDTO(Class object, String field, Object value,  String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = value;
        this.message = message;
    }

    public Class getObject() {
        return object;
    }

    public void setObject(Class object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
