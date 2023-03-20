package ru.bendricks.employeeadministratoion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("No user with such email");
        }
        return new UserDetailsInfo(user.get());
    }
}
