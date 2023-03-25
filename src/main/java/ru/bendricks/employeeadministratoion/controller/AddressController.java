package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.bendricks.employeeadministratoion.dto.entity.AddressDTO;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.AddressCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.mapper.address.AddressMapper;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.service.AddressService;
import ru.bendricks.employeeadministratoion.util.AddressValidator;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;
    private final AddressValidator addressValidator;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressController(AddressService addressService, AddressValidator addressValidator, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressValidator = addressValidator;
        this.addressMapper = addressMapper;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO addAddress(@RequestBody @Valid AddressCreateDTO addressCreateDTO, BindingResult bindingResult) {
        addressValidator.validate(addressCreateDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return addressMapper.toDTO(
                addressService.create(
                        addressMapper.toModel(
                                addressCreateDTO
                        )
                )
        );
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO changeAddress(@RequestBody @Valid AddressDTO addressDTO, BindingResult bindingResult) {
        addressValidator.validateForChange(addressDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return addressMapper.toDTO(
                addressService.update(
                        addressMapper.toModel(
                                addressDTO
                        )
                )
        );
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContractById(@RequestBody @Valid AddressDTO addressDTO, BindingResult bindingResult) {
        addressValidator.validateForChange(addressDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        addressService.delete(addressDTO.getId());
    }

    private void checkForAddChangeErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Address not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address getAddressById(@PathVariable("id") int id) {
        return addressService.findById(id);
    }

}
