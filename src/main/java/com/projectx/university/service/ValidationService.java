package com.projectx.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final Validator validator;

    public <T> T validateInput(final T object) {
        final Errors errors = new BeanPropertyBindingResult(object, object.getClass().getName());
        validator.validate(object, errors);
        if (errors.hasErrors()) {
            final String errorMessages = errors.getAllErrors().stream()
                    .map(ValidationService::getErrorMessage)
                    .sorted()
                    .collect(joining("; "));
            throw new ServerWebInputException(errorMessages);
        }
        return object;
    }

    private static String getErrorMessage(ObjectError error) {
        return error instanceof FieldError
                ? String.format("'%s' property %s", ((FieldError) error).getField(), error.getDefaultMessage())
                : error.getDefaultMessage();
    }
}
