package ru.bendricks.employeeadministratoion.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.service.UserService;

@Component
public class AddressValidator implements Validator {

    private final UserService userService;

    @Autowired
    public AddressValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Address.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Address address = (Address) target;
        if (!userService.isUserExists(address.getUser().getId())) {
            errors.rejectValue("user", "", "No such user");
        }
    }
}
