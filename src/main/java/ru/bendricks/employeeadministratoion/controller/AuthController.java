package ru.bendricks.employeeadministratoion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.bendricks.employeeadministratoion.dto.AuthenticationRequestDTO;
import ru.bendricks.employeeadministratoion.dto.AuthenticationResponse;
import ru.bendricks.employeeadministratoion.dto.MessageResponse;
import ru.bendricks.employeeadministratoion.dto.PasswordChangeDTO;
import ru.bendricks.employeeadministratoion.security.UserDetailsInfo;
import ru.bendricks.employeeadministratoion.security.service.AuthService;
import ru.bendricks.employeeadministratoion.security.util.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse performLogin(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(
                (
                        (UserDetailsInfo) authService.loadUserByUsername(
                                authenticationRequestDTO.getEmail()
                        )
                ).getUser()
        );
        return new AuthenticationResponse(
            token,
            System.currentTimeMillis()
        );
    }

    @PostMapping("/password/change")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.changePassword(userDetailsInfo.getUser(), passwordChangeDTO);
        return new MessageResponse(
                "Updated successful",
                System.currentTimeMillis()
        );
    }

}
