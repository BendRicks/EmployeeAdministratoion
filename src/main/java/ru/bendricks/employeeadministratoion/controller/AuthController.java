package ru.bendricks.employeeadministratoion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.employeeadministratoion.dto.AuthenticationDTO;
import ru.bendricks.employeeadministratoion.dto.PasswordChangeDTO;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.security.UserDetailsInfo;
import ru.bendricks.employeeadministratoion.security.service.AuthService;
import ru.bendricks.employeeadministratoion.security.util.JWTUtil;

import java.util.Map;

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
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(
                (
                        (UserDetailsInfo) authService.loadUserByUsername(
                                authenticationDTO.getEmail()
                        )
                ).getUser()
        );
        return new ResponseEntity<>(
                Map.of("jwt_token", token),
                HttpStatus.OK
        );
    }

    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO, @AuthenticationPrincipal User user){
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.changePassword(userDetailsInfo.getUser(), passwordChangeDTO);
        return new ResponseEntity<>(
                "Updated successful",
                HttpStatus.OK
        );
    }

}
