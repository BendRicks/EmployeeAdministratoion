package ru.bendricks.employeeadministratoion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.thymeleaf.exceptions.TemplateInputException;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.dto.MessageResponse;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUserNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getEntityName().concat(" not found"),
                        System.currentTimeMillis()
                ), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUserNotCreatedException(NotCreatedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleUserNotCreatedException(TemplateInputException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNullPointerException(NullPointerException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Some params are missing or are null",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Illegal params values",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleNumberFormatException(NumberFormatException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Incorrect number format",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Bad credentials",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(
                new MessageResponse(
                        "Not authorized",
                        System.currentTimeMillis()
                ), HttpStatus.BAD_REQUEST
        );
    }

}
