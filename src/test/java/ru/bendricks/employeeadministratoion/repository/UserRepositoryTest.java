package ru.bendricks.employeeadministratoion.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.bendricks.employeeadministratoion.model.User;
import ru.bendricks.employeeadministratoion.model.UserRole;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @Order(1)
    @DisplayName(value = "Loading")
    void loading(){
        assertThat(userRepository).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Save user")
    void insertUser(){
        User user = new User();
        user.setNameEn("admin");
        user.setNameRu("admin");
        user.setSurnameEn("admin");
        user.setSurnameRu("admin");
        user.setNameByFatherRu("admin");
        user.setEmail("admin@admin.com");
        user.setCreationTime(LocalDateTime.now());
        user.setPassportId("kdiecdkefdswkf");
        user.setPassword("$2a$10$o.fuD0J.O5CLZKqNS8Rs7usrqXXZtHbh0pgjt1TH2UI3kOYqYcQoa");
        user.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName(value = "Get user by email")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByEmail() {
        Optional<User> userOptional1 = userRepository.findByEmail("admin@admin.com");
        Optional<User> userOptional2 = userRepository.findByEmail("employee@employee.com");
        assertThat(userOptional1).isPresent();
        assertThat(userOptional2).isPresent();
        assertThat(userOptional1.get().getNameEn()).isEqualTo("admin");
        assertThat(userOptional2.get().getNameEn()).isEqualTo("employee");
    }

    @Test
    @Order(4)
    @DisplayName(value = "Delete user")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteUser() {
        userRepository.deleteById(1);
        userRepository.deleteById(2);
        assertThat(userRepository.findByEmail("admin@admin.com")).isEmpty();
        assertThat(userRepository.findByEmail("employee@employee.com")).isEmpty();
    }

}