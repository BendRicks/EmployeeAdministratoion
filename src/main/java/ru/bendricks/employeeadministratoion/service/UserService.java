package ru.bendricks.employeeadministratoion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.exception.UserNotFoundException;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User findById(int id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public boolean isUserExists(int id){
        return userRepository.findById(id).isPresent();
    }

}
