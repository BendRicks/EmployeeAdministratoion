package ru.bendricks.employeeadministratoion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.bendricks.employeeadministratoion.mapper.UserMapper;
import ru.bendricks.employeeadministratoion.security.util.JWTUtil;

@Controller
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(JWTUtil jwtUtil, UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

}
