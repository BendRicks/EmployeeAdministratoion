package ru.bendricks.employeeadministratoion.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.repository.UserRepository;

import java.nio.file.AccessDeniedException;
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
        return userRepository.findAllByOrderByIdAsc();
    }

    public User findById(int id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public User update(User user){
        return userRepository.save(
                merge(
                        findById(user.getId()),
                        user
                )
        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private User merge(User dest, User src){
        if (src.getNameEn() != null) {
            dest.setNameEn(src.getNameEn());
        }
        if (src.getNameRu() != null) {
            dest.setNameRu(src.getNameRu());
        }
        if (src.getSurnameEn() != null) {
            dest.setSurnameEn(src.getSurnameEn());
        }
        if (src.getSurnameRu() != null) {
            dest.setSurnameRu(src.getSurnameRu());
        }
        if (src.getNameByFatherRu() != null) {
            dest.setNameByFatherRu(src.getNameByFatherRu());
        }
        if (src.getNameByFatherRu() != null) {
            dest.setNameByFatherRu(src.getNameByFatherRu());
        }
        if (src.getEmail() != null) {
            dest.setEmail(src.getEmail());
        }
        if (src.getCreationTime() != null) {
            dest.setCreationTime(src.getCreationTime());
        }
        if (src.getPassportId() != null) {
            dest.setPassportId(src.getPassportId());
        }
        if (src.getRole() != null) {
            dest.setRole(src.getRole());
        }
        return dest;
    }

}
