package ru.bendricks.employeeadministratoion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.ContractCreateDTO;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.service.ContractService;
import ru.bendricks.employeeadministratoion.service.UserService;

@Component
public class ContractValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ContractDTO.class) || clazz.equals(ContractCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContractCreateDTO contractCreateDTO = (ContractCreateDTO) target;
        if (contractCreateDTO.getUser() == null
                || contractCreateDTO.getUser().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }

    public void validateForChange(Object target, Errors errors) {
        ContractDTO contractDTO = (ContractDTO) target;
        if (contractDTO.getId() == null){
            errors.rejectValue("id", "", "Contract id not found");
        }
        if (contractDTO.getUser() != null
                && contractDTO.getUser().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }

}
