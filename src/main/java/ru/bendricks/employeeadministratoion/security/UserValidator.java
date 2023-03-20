package ru.bendricks.employeeadministratoion.security;

import ru.bendricks.employeeadministratoion.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final RegistrationService registrationService;

    @Autowired
    public UserValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        if (!registrationService.isEmailAvailable(user.getEmail()))
            errors.rejectValue("email", "", "User with such username already exists");
    }
}
