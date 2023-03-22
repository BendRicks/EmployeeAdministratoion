package ru.bendricks.employeeadministratoion.exception;

public class ContractNotFoundException extends RuntimeException{

    public ContractNotFoundException() {
    }

    public ContractNotFoundException(String message) {
        super(message);
    }
}
