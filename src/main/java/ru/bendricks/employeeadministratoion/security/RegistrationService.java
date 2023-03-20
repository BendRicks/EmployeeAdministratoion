package ru.bendricks.employeeadministratoion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isEmailAvailable(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    public User create(User user){
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole("ROLE_USER");
        user.setCreationTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
