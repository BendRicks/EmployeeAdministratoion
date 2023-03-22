package ru.bendricks.employeeadministratoion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bendricks.employeeadministratoion.exception.AddressNotFoundException;
import ru.bendricks.employeeadministratoion.exception.ContractNotFoundException;
import ru.bendricks.employeeadministratoion.exception.UserNotFoundException;
import ru.bendricks.employeeadministratoion.util.ErrorResponse;

@ControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleContractNotFound(ContractNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "No such contract",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAddressNotFound(AddressNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "No such address",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "No such user",
                        System.currentTimeMillis()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Incorrect json params",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Illegal json fields values",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Incorrect number format",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "Bad credentials",
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
