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
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.model.ContractType;
import ru.bendricks.employeeadministratoion.model.EmploymentType;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContractRepositoryTest {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractRepositoryTest(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Test
    @Order(1)
    @DisplayName(value = "Loading")
    void loading(){
        assertThat(contractRepository).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Save contract")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void insertAddress(){
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);
        Contract contract1 = new Contract();
        Contract contract2 = new Contract();
        contract1.setUser(user1);
        contract2.setUser(user2);
        contract1.setEmploymentDate(LocalDateTime.now());
        contract2.setEmploymentDate(LocalDateTime.now());
        contract1.setEndOfWorkDate(LocalDateTime.now().plusYears(2));
        contract2.setEndOfWorkDate(LocalDateTime.now().plusYears(2));
        contract1.setSalary(BigDecimal.valueOf(1700));
        contract2.setSalary(BigDecimal.valueOf(1800));
        contract1.setSalaryIBAN("fdsfjhgpiungiurhgieurhgisdaf");
        contract2.setSalaryIBAN("fdsfjhgpiungiurhgieurhgisdaf");
        contract1.setContractType(ContractType.TEMPORARY);
        contract2.setContractType(ContractType.UNTIMELY);
        contract1.setEmploymentType(EmploymentType.FULL_TIME);
        contract2.setEmploymentType(EmploymentType.FULL_TIME);
        contract1.setContractStatus(RecordStatus.OPERATING);
        contract2.setContractStatus(RecordStatus.OPERATING);
        assertThat(contract1.getId()).isNull();
        assertThat(contract2.getId()).isNull();
        contractRepository.save(contract1);
        contractRepository.save(contract2);
        assertThat(contract1.getId()).isNotNull();
        assertThat(contract2.getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName(value = "Delete address")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteAddress() {
        contractRepository.deleteById(1);
        contractRepository.deleteById(2);
        assertThat(contractRepository.findById(1)).isEmpty();
        assertThat(contractRepository.findById(2)).isEmpty();
    }

}