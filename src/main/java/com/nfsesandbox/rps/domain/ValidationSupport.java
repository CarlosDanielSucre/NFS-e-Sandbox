package com.nfsesandbox.rps.domain;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ValidationSupport {
    static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    static String requireNonBlank(String value, String name) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), name));
        }
        return value.strip();
    }
    static <T> T requireNonNull(T value, String name) {
        if(value == null) {
            throw new IllegalArgumentException(MessageFormat.format(resourceBundle.getString("validation.required"), name));
        }
        return value;
    }
}
