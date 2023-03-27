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
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.model.User;


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
    @DisplayName(value = "Save address")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void insertAddress(){
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        Address address1 = new Address();
        Address address2 = new Address();
        address1.setUser(user1);
        address2.setUser(user2);
        address1.setCity("Minsk");
        address2.setCity("Minsk");
        address1.setDistrict("Leninsky");
        address2.setDistrict("Centralniy");
        address1.setStreet("Kirova");
        address2.setStreet("Pushkina");
        address1.setBuilding("19");
        address2.setBuilding("30");
        address1.setRoom("19");
        address2.setRoom("40");
        address1.setAddressStatus(RecordStatus.OPERATING);
        address2.setAddressStatus(RecordStatus.OPERATING);
        assertThat(address1.getId()).isNull();
        assertThat(address2.getId()).isNull();
        addressRepository.save(address1);
        addressRepository.save(address2);
        assertThat(address1.getId()).isNotNull();
        assertThat(address2.getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName(value = "Delete address")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteAddress() {
        addressRepository.deleteById(1);
        addressRepository.deleteById(2);
        assertThat(addressRepository.findById(1)).isEmpty();
        assertThat(addressRepository.findById(2)).isEmpty();
    }

}