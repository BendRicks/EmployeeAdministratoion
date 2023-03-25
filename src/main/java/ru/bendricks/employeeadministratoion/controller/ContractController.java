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

import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.ContractCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.mapper.contract.ContractMapper;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.service.ContractService;
import ru.bendricks.employeeadministratoion.util.ContractValidator;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    private final ContractService contractService;
    private final ContractValidator contractValidator;
    private final ContractMapper contractMapper;

    @Autowired
    public ContractController(ContractService contractService, ContractValidator contractValidator, ContractMapper contractMapper) {
        this.contractService = contractService;
        this.contractValidator = contractValidator;
        this.contractMapper = contractMapper;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDTO addContract(@RequestBody @Valid ContractCreateDTO contractCreateDTO, BindingResult bindingResult) {
        contractValidator.validate(contractCreateDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return contractMapper.toDTO(
                contractService.create(
                        contractMapper.toModel(
                                contractCreateDTO
                        )
                )
        );
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDTO changeContract(@RequestBody @Valid ContractDTO contractDTO, BindingResult bindingResult) {
        contractValidator.validateForChange(contractDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return contractMapper.toDTO(
                contractService.update(
                        contractMapper.toModel(
                                contractDTO
                        )
                )
        );
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContractById(@RequestBody @Valid ContractDTO contractDTO, BindingResult bindingResult) {
        contractValidator.validateForChange(contractDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        contractService.delete(contractDTO.getId());
    }

    private void checkForAddChangeErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Contract not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contract getContractById(@PathVariable("id") int id) {
        return contractService.findById(id);
    }

}
