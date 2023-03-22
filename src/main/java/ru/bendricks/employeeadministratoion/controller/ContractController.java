package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.service.ContractService;
import ru.bendricks.employeeadministratoion.util.ContractValidator;
import ru.bendricks.employeeadministratoion.util.ErrorResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    private final ContractService contractService;
    private final ContractValidator contractValidator;

    @Autowired
    public ContractController(ContractService contractService, ContractValidator contractValidator) {
        this.contractService = contractService;
        this.contractValidator = contractValidator;
    }

    @PostMapping("/add_or_change")
    public ResponseEntity<Contract> addContract(@RequestBody @Valid Contract contract, BindingResult bindingResult) {
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Contract not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
        return new ResponseEntity<>(
                contractService.create(contract),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Contract> getAddressById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                contractService.getContractById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/change_status")
    public ResponseEntity<String> changeAddressStatus(@RequestBody Map<String, String> params) {
        int id = Integer.parseInt(params.get("id"));
        RecordStatus status = RecordStatus.valueOf(params.get("status"));
        contractService.changeStatusById(id, status);
        return new ResponseEntity<>(
                "Updated successful",
                HttpStatus.OK
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotCreatedException(NotCreatedException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
