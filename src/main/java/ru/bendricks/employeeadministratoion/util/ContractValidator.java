package ru.bendricks.employeeadministratoion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.service.UserService;

@Component
public class ContractValidator implements Validator {

    private final UserService userService;

    @Autowired
    public ContractValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Contract.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contract contract = (Contract) target;
        if (!userService.isUserExists(contract.getUser().getId())) {
            errors.rejectValue("user", "", "No such user");
        }
    }
}
