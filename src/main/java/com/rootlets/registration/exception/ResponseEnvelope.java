package com.rootlets.registration.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Response envelope.
 *
 * @param <T> the type parameter
 * @author g523150
 * @version 1.0
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class ResponseEnvelope<T> {
    private boolean success;
    private T data;
    private String path;
    private String timeStamp;
    private List<FieldErrorDTO> errors = new ArrayList<>();
    private String message;

    /**
     * Instantiates a new Response envelope.
     */
    public ResponseEnvelope() {
        // default

    }

    /**
     * Instantiates a new Response envelope.
     *
     * @param body    the body
     * @param path    the path
     * @param success the success
     */
    public ResponseEnvelope(T body, String path, boolean success) {
        this.success = success;
        this.data = body;
        this.path = path;
    }
}
