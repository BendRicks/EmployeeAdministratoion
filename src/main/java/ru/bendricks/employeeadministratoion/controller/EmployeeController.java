package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
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

import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.mapper.user.UserListMapper;
import ru.bendricks.employeeadministratoion.mapper.user.UserMapper;
import ru.bendricks.employeeadministratoion.security.service.AuthService;
import ru.bendricks.employeeadministratoion.security.util.UserValidator;
import ru.bendricks.employeeadministratoion.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final UserService userService;
    private final AuthService authService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;

    @Autowired
    public EmployeeController(UserService userService, AuthService authService, UserValidator userValidator, UserMapper userMapper, UserListMapper userListMapper) {
        this.userService = userService;
        this.authService = authService;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.userListMapper = userListMapper;
    }


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addEmployee(@RequestBody @Valid UserCreateDTO userCreateDTO, BindingResult bindingResult) {
        userValidator.validate(userCreateDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        return userMapper.toDTO(
                        authService.create(
                                userMapper.toModel(
                                        userCreateDTO
                                )
                        )
                );
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO changeEmployee(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validateForChange(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("User not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }
            throw new NotCreatedException(errorMessage.toString());
        }
        return userMapper.toDTO(
                userService.update(
                        userMapper.toModel(
                                userDTO
                        )
                )
        );
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContractById(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validateForChange(userDTO, bindingResult);
        checkForAddChangeErrors(bindingResult);
        userService.delete(userDTO.getId());
    }

    private void checkForAddChangeErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("User not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            String errMsg = errorMessage.toString();
            throw new NotCreatedException(errMsg);
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllEmployees(){
        return userListMapper.toDTO(userService.getUsers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getEmployee(@PathVariable int id) {
        return userMapper.toDTO(userService.findById(id));
    }

}
