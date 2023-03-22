package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.service.AddressService;
import ru.bendricks.employeeadministratoion.util.AddressValidator;

import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;
    private final AddressValidator addressValidator;

    @Autowired
    public AddressController(AddressService addressService, AddressValidator addressValidator) {
        this.addressService = addressService;
        this.addressValidator = addressValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody @Valid Address address, BindingResult bindingResult) {
        addressValidator.validate(address, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Address not created").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
        return new ResponseEntity<>(
                addressService.create(address),
                HttpStatus.OK
        );
    }

    @PostMapping("/change")
    public ResponseEntity<Address> changeAddress(@RequestBody @Valid Address address, BindingResult bindingResult) {
        addressValidator.validate(address, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Address not changed/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
        return new ResponseEntity<>(
                addressService.create(address),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getContractById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                addressService.getContractById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/change_status")
    public ResponseEntity<String> changeContractStatus(@RequestBody Map<String, String> params) {
        int id = Integer.parseInt(params.get("id"));
        RecordStatus status = RecordStatus.valueOf(params.get("status"));
        addressService.changeStatusById(id, status);
        return new ResponseEntity<>(
                "Updated successful",
                HttpStatus.OK
        );
    }

}
