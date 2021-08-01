package com.rootlets.registration.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Custom response entity exception handler.
 */
@ControllerAdvice
@Order(1)
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LogManager.getLogger(CustomResponseEntityExceptionHandler.class);


    /**
     * Instantiates a new Custom response entity exception handler.
     */
    public CustomResponseEntityExceptionHandler() {
        super();
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldErrorDTO> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        for (FieldError fieldError : fieldErrors) {
            FieldErrorDTO error = new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            FieldErrorDTO error = new FieldErrorDTO(objectError.getObjectName(), objectError.getDefaultMessage());
            errors.add(error);
        }

        ResponseEnvelope errorMessage = new ResponseEnvelope();
        errorMessage.setErrors(errors);
        errorMessage.setSuccess(false);

        return new ResponseEntity<>(errorMessage, headers, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getErrorResponse(ex, request), headers, status);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseEnvelope errorResponse = getErrorResponse(ex, request);

        if (ex.getMostSpecificCause() instanceof InvalidFormatException) {
            InvalidFormatException mostSpecificCause = (InvalidFormatException) ex.getMostSpecificCause();
            FieldErrorDTO errorDTO = new FieldErrorDTO();
            errorDTO.setError(ex.getClass().getSimpleName());
            errorDTO.setErrorDescription(mostSpecificCause.getOriginalMessage());
            errorResponse.setErrors(Collections.singletonList(errorDTO));
            errorResponse.setMessage("Invalid target type or value " + mostSpecificCause.getValue().toString());
        }
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getErrorResponse(ex, request), HttpStatus.NOT_FOUND);
    }

    /**
     * Customize the response for HttpRequestMethodNotSupportedException.
     * <p>This method logs a warning, sets the "Allow" header, and delegates to
     * {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for HttpMediaTypeNotAcceptableException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for MissingPathVariableException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     * @since 4.2
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for MissingServletRequestParameterException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for ServletRequestBindingException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for ConversionNotSupportedException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for HttpMessageNotWritableException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for MissingServletRequestPartException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Customize the response for BindException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), headers, status, request);
    }

    /**
     * Handle constraint violation response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @param headers the headers
     * @return the response entity
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request, HttpHeaders headers) {
        List<FieldErrorDTO> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            FieldErrorDTO error = new FieldErrorDTO(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath(), violation.getMessage());
            errors.add(error);
        }
        ResponseEnvelope errorMessage = new ResponseEnvelope();
        errorMessage.setErrors(errors);
        errorMessage.setSuccess(false);

        return new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
    }
    // API

    // 400




    // 409

    /**
     * Handle conflict response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({DataAccessException.class, InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    /**
     * Handle not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, final WebRequest request) {
        return new ResponseEntity<>(getErrorResponse(ex, request), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


    /**
     * Handle not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleNotFound(MethodArgumentTypeMismatchException ex, final WebRequest request) {
        ResponseEnvelope errorResponse = getErrorResponse(ex, request);

        if (ex.getMostSpecificCause() instanceof NumberFormatException) {
            NumberFormatException mostSpecificCause = (NumberFormatException) ex.getMostSpecificCause();
            FieldErrorDTO errorDTO = new FieldErrorDTO();
            errorDTO.setError(ex.getClass().getSimpleName());
            errorDTO.setErrorDescription(mostSpecificCause.getMessage());
            errorResponse.setErrors(Collections.singletonList(errorDTO));
            errorResponse.setMessage("Invalid input type " + mostSpecificCause.getMessage().toLowerCase());
        }
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 500

    /**
     * Handle internal response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, Exception.class})
    public ResponseEntity<Object> handleInternal(final Exception ex, final WebRequest request) {
        return handleExceptionInternal(ex, getErrorResponse(ex, request), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEnvelope getErrorResponse(Exception exception, WebRequest req) {
        ResponseEnvelope response = new ResponseEnvelope<>();
        FieldErrorDTO errorDTO = new FieldErrorDTO();
        errorDTO.setError(exception.getClass().getSimpleName());
        errorDTO.setErrorDescription(exception.getMessage());
        response.setSuccess(false);
        response.setPath(req.getContextPath());
        response.setTimeStamp(getCurrenDateTime());
        response.setMessage(exception.getMessage());
        response.getErrors().add(errorDTO);

        return response;
    }

    private String getCurrenDateTime() {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }


}