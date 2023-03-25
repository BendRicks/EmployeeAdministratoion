package ru.bendricks.employeeadministratoion.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.bendricks.employeeadministratoion.dto.PasswordChangeDTO;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.model.UserRole;
import ru.bendricks.employeeadministratoion.repository.UserRepository;
import ru.bendricks.employeeadministratoion.security.UserDetailsInfo;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("No user with such email");
        }
        return new UserDetailsInfo(user.get());
    }

    @Transactional
    public void changePassword(User userToChange, PasswordChangeDTO passwordChangeDTO) throws UsernameNotFoundException {
        if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), userToChange.getPassword())) {
            userToChange.setPassword(
                    passwordEncoder.encode(
                            passwordChangeDTO.getNewPassword()
                    )
            );
            userRepository.save(userToChange);
        }
    }

    public boolean isEmailAvailable(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public User create(User user){
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(UserRole.ROLE_EMPLOYEE);
        user.setCreationTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
