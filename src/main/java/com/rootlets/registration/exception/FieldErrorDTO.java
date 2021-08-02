package com.rootlets.registration.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Field error dto.
 *
 * @author Arpit Aggarwal g
 * @version 1.0
 */
@Setter
@Getter
public class FieldErrorDTO {
    private String error;
    private String errorDescription;

    /**
     * Instantiates a new Field error dto.
     */
    public FieldErrorDTO() {
        // No parameterized constructor required with parameterized to create object
    }

    /**
     * Instantiates a new Field error dto.
     *
     * @param fieldName  the field name
     * @param fieldError the field error
     */
    public FieldErrorDTO(String fieldName, String fieldError) {
        this.error = fieldName;
        this.errorDescription = fieldError;
    }
}
