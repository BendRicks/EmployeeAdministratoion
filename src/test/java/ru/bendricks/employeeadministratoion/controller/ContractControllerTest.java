package ru.bendricks.employeeadministratoion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.bendricks.employeeadministratoion.EmployeeAdministrationApplication;
import ru.bendricks.employeeadministratoion.dto.MessageResponse;
import ru.bendricks.employeeadministratoion.dto.entity.AddressDTO;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.AddressCreateDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.ContractCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.model.ContractType;
import ru.bendricks.employeeadministratoion.model.EmploymentType;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.service.AddressService;
import ru.bendricks.employeeadministratoion.service.ContractService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {EmployeeAdministrationApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContractControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ContractService contractService;

    @Autowired
    public ContractControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, ContractService contractService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.contractService = contractService;
    }

    @Test
    @Order(1)
    void loading(){
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(contractService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Add contract fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddContractFail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(5);
        ContractCreateDTO contractCreateDTO = new ContractCreateDTO(userDTO, null, LocalDateTime.now().plusYears(2), BigDecimal.valueOf(1700), "123456789013456789012345678", null, EmploymentType.FULL_TIME, RecordStatus.OPERATING);
        System.out.println(objectMapper.writeValueAsString(contractCreateDTO));
        String resp = mockMvc.perform(post("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(3)
    @DisplayName(value = "Add contract")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddContract() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        ContractCreateDTO contractCreateDTO = new ContractCreateDTO(userDTO, LocalDateTime.now(), LocalDateTime.now().plusYears(2), BigDecimal.valueOf(1700), "1234567890123456789012345678", ContractType.TEMPORARY, EmploymentType.FULL_TIME, RecordStatus.OPERATING);
        System.out.println(objectMapper.writeValueAsString(contractCreateDTO));
        String resp = mockMvc.perform(post("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, ContractDTO.class).getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName(value = "Change contract fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testChangeContractFail() throws Exception {
        ContractDTO contractDTO = new ContractDTO(5, null, null, null, null, "tesIban12345678901234567890", null, null, null);
        String resp = mockMvc.perform(put("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(5)
    @DisplayName(value = "Change contract")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testChangeContract() throws Exception {
        ContractDTO contractDTO = new ContractDTO(1, null, null, null, null, "testIban12345678901234567890", null, null, null);
        String resp = mockMvc.perform(put("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(contractService.findById(1).getSalaryIBAN()).isEqualTo("testIban12345678901234567890");
    }

    @Test
    @Order(6)
    @DisplayName(value = "Delete contract fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteContractByIdFail() throws Exception {
        ContractDTO contractDTO = new ContractDTO(5, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(7)
    @DisplayName(value = "Delete contract")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteContractById() throws Exception {
        ContractDTO contractDTO = new ContractDTO(1, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/contract/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(contractDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThrows(NotFoundException.class, () -> contractService.findById(1));
    }

    @Test
    @Order(8)
    @DisplayName(value = "Get contract fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetContractByIdFail() throws Exception {
        String resp = mockMvc.perform(get("/api/contract/5")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(9)
    @DisplayName(value = "Get contract")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_contracts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetContractById() throws Exception {
        String resp = mockMvc.perform(get("/api/contract/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, ContractDTO.class).getSalaryIBAN()).isEqualTo("hdkfnmcksjkdoikfjoadsdfdsdad");
    }
}