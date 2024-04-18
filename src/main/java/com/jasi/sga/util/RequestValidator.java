package com.jasi.sga.util;

import java.util.HashMap;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract interface RequestValidator {
    public static boolean isInvalid(BindingResult validation) {
        return validation.hasErrors();
    }

    public static HashMap<String, String> getAllErrors(BindingResult validation) {
        HashMap<String, String> errors = new HashMap<>();
        validation.getFieldErrors().forEach((FieldError error) -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
