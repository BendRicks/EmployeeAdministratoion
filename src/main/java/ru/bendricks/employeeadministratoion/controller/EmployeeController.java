package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.dto.UserDTO;
import ru.bendricks.employeeadministratoion.exception.UserNotCreatedException;
import ru.bendricks.employeeadministratoion.exception.UserNotFoundException;
import ru.bendricks.employeeadministratoion.mapper.UserMapper;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.security.RegistrationService;
import ru.bendricks.employeeadministratoion.security.UserValidator;
import ru.bendricks.employeeadministratoion.service.UserService;
import ru.bendricks.employeeadministratoion.util.ErrorResponse;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    @Autowired
    public EmployeeController(UserService userService, RegistrationService registrationService, UserValidator userValidator, UserMapper userMapper) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addEmployee(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }

            throw new UserNotCreatedException(errorMessage.toString());
        }
        UserDTO createdUserDTO = userMapper.toDTO(
                registrationService.create(
                        userMapper.toModel(userDTO)
                )
        );
        return new ResponseEntity<>(createdUserDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public UserDTO getEmployee(@PathVariable int id, Model model) {
        return userMapper.toDTO(userService.findById(id));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotCreatedException(UserNotCreatedException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        exception.getMessage(),
                        System.currentTimeMillis()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        "No such user",
                        System.currentTimeMillis()
                ),
                HttpStatus.NOT_FOUND
        );
    }

}
