package com.postbox.config.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class CustomErrorDto {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    private String message;
    private String debugMessage;

    private List<CustomValidationErrorDTO> validationErrorDTOs;

    private CustomErrorDto() {
        timestamp = LocalDateTime.now();
    }

    CustomErrorDto(HttpStatus status) {
        this();
        this.status = status;
    }

    CustomErrorDto(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    CustomErrorDto(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<CustomValidationErrorDTO> getValidationErrorDTOs() {
        return validationErrorDTOs;
    }

    public void setValidationErrorDTOs(List<CustomValidationErrorDTO> validationErrorDTOs) {
        this.validationErrorDTOs = validationErrorDTOs;
    }
}