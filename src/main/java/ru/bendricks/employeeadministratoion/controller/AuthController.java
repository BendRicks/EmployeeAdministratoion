package ru.bendricks.employeeadministratoion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.dto.AuthenticationDTO;
import ru.bendricks.employeeadministratoion.mapper.UserMapper;
import ru.bendricks.employeeadministratoion.security.UserDetailsInfo;
import ru.bendricks.employeeadministratoion.security.service.LoginService;
import ru.bendricks.employeeadministratoion.security.util.JWTUtil;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginService loginService;
    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(LoginService loginService, JWTUtil jwtUtil, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        }

        String token = jwtUtil.generateToken(
                (
                        (UserDetailsInfo)loginService.loadUserByUsername(
                                authenticationDTO.getEmail()
                        )
                ).getUser()
        );
        return Map.of("jwt_token", token);
    }

}
