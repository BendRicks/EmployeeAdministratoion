package ru.bendricks.employeeadministratoion.security.util;

import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bendricks.employeeadministratoion.security.service.AuthService;

@Component
public class UserValidator implements Validator {

    private final AuthService authService;

    @Autowired
    public UserValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDTO user = (UserCreateDTO) target;
        if (!authService.isEmailAvailable(user.getEmail()))
            errors.rejectValue("email", "", "User with such username already exists");
    }

    public void validateForChange(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (!authService.isEmailAvailable(userDTO.getEmail()))
            errors.rejectValue("email", "", "User with such username already exists");
        if (userDTO.getId() == null || userDTO.getId() == 0){
            errors.rejectValue("id", "", "Incorrect id");
        }
    }

}
