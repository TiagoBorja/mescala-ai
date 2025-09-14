package com.tiagoborja.mescala_ai.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException exception,
                                                                    HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return buildProblemDetail(
                URI.create("blank:blank"),
                "Validation Failed",
                HttpStatus.BAD_REQUEST,
                "One or more fields have errors",
                URI.create(request.getRequestURI()),
                errors
        );
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException exception,
                                                                 HttpServletRequest request) {
        return buildProblemDetail(
                URI.create("blank:blank"),
                "Resource Not Found",
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                URI.create(request.getRequestURI()),
                null
        );
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<ProblemDetail> handleAlreadyExistsException(AlreadyExistsException exception,
                                                                      HttpServletRequest request) {
        return buildProblemDetail(
                URI.create("blank:blank"),
                "Resource Already Exists",
                HttpStatus.CONFLICT,
                exception.getMessage(),
                URI.create(request.getRequestURI()),
                null
        );
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(URI type,
                                                             String title,
                                                             HttpStatus status,
                                                             String detail,
                                                             URI instance,
                                                             Map<String, String> errors) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);

        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setStatus(status);
        problemDetail.setDetail(detail);
        problemDetail.setInstance(instance);
        if (errors != null) {
            problemDetail.setProperty("errors", errors);
        }
        return ResponseEntity.status(status).body(problemDetail);
    }
}
