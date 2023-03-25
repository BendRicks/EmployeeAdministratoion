package ru.bendricks.employeeadministratoion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.bendricks.employeeadministratoion.dto.entity.AddressDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.AddressCreateDTO;
import ru.bendricks.employeeadministratoion.service.AddressService;
import ru.bendricks.employeeadministratoion.service.UserService;

@Component
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AddressDTO.class) || clazz.equals(AddressCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressCreateDTO address = (AddressCreateDTO) target;
        if (address.getUserDTO() == null
                || address.getUserDTO().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }

    public void validateForChange(Object target, Errors errors) {
        AddressDTO addressDTO = (AddressDTO) target;
        if (addressDTO.getId() == null){
            errors.rejectValue("id", "", "Address id not found");
        }
        if (addressDTO.getUserDTO() != null
                && addressDTO.getUserDTO().getId() == null) {
            errors.rejectValue("user", "", "User id not found");
        }
    }
}
