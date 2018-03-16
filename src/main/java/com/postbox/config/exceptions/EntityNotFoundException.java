package com.postbox.config.exceptions;

public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a EntityNotFoundException with null
     * as its error message string.
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructs a EntityNotFoundException, saving a reference
     * to the error message string `s` for later retrieval by the
     * `getMessage` method.
     *
     * @param s the detail message.
     */
    public EntityNotFoundException(String s) {
        super(s);
    }
}
