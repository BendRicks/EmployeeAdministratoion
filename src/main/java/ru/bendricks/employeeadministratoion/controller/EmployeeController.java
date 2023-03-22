package ru.bendricks.employeeadministratoion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.dto.UserDTO;
import ru.bendricks.employeeadministratoion.exception.NotCreatedException;
import ru.bendricks.employeeadministratoion.mapper.UserListMapper;
import ru.bendricks.employeeadministratoion.mapper.UserMapper;
import ru.bendricks.employeeadministratoion.security.service.AuthService;
import ru.bendricks.employeeadministratoion.security.util.UserValidator;
import ru.bendricks.employeeadministratoion.service.UserService;

import java.util.List;

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


    @PostMapping("/add_or_change")
    public ResponseEntity<UserDTO> addEmployee(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("User not created/changed").append(" = ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
        return new ResponseEntity<>(
                userMapper.toDTO(
                        authService.create(
                                userMapper.toModel(userDTO)
                        )
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllEmployees(){
        return new ResponseEntity<>(
                userListMapper.toDTO(userService.getUsers()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getEmployee(@PathVariable int id, Model model) {
        return new ResponseEntity<>(
                userMapper.toDTO(userService.findById(id)),
                HttpStatus.OK
        );
    }

}
