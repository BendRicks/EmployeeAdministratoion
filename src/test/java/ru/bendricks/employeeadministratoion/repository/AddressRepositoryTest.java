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
class AddressRepositoryTest {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressRepositoryTest(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Test
    @Order(1)
    @DisplayName(value = "Loading")
    void loading(){
        assertThat(addressRepository).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Save user")
    void insertAddress(){

    }

    @Test
    @Order(4)
    @DisplayName(value = "Delete user")
//    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteAddress() {

    }

}