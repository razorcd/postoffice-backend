package com.postbox.config.errorhandling;

import com.postbox.config.exceptions.EntityNotFoundException;
import com.postbox.config.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDto elementNotFound(EntityNotFoundException e) {
        return new CustomErrorDto(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomErrorDto validationError(ValidationException e) {
        CustomErrorDto customErrorDto = new CustomErrorDto(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);

        List<CustomValidationErrorDTO> customValidationErrorDTOList = e.getExceptionFields().stream()
                .map(exp -> new CustomValidationErrorDTO(exp.getObject(), exp.getField(), exp.getValue(), exp.getLocalizedMessage()))
                .collect(Collectors.toList());
        customErrorDto.setValidationErrorDTOs(customValidationErrorDTOList);
        return customErrorDto;
    }
}
