package ru.bendricks.employeeadministratoion.exception;

public class AddressNotFoundException extends RuntimeException{

    public AddressNotFoundException() {
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
