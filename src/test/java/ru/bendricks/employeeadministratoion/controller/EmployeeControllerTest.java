package ru.bendricks.employeeadministratoion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.service.UserService;

import java.util.List;

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
class EmployeeControllerTest {


    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public EmployeeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, UserService userService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Test
    @Order(1)
    void loading(){
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Add employee fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddEmployeeFail() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("A", "А", "S", "С", "О", "bendricksyandex.ru", "5930403A01PB8");
        String resp = mockMvc.perform(post("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(3)
    @DisplayName(value = "Add employee")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddEmployee() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("Alexandr", "Александр", "Savchuk", "Савчук", "Олегович", "bendricks@yandex.ru", "5930403A001PB8");
        String resp = mockMvc.perform(post("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(userService.getUsers().size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Change employee fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changeEmployeeFail() throws Exception {
        UserDTO userDTO = new UserDTO(5, "A", null, null, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(put("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(5)
    @DisplayName(value = "Change employee")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changeEmployee() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Alexandr", null, null, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(put("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(userService.findById(1).getNameEn()).isEqualTo("Alexandr");
    }

    @Test
    @Order(6)
    @DisplayName(value = "Delete employee fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteContractByIdFail() throws Exception {
        UserDTO userDTO = new UserDTO(5, null, null, null, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(7)
    @DisplayName(value = "Delete employee")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteContractById() throws Exception {
        UserDTO userDTO = new UserDTO(1, null, null, null, null, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/employee/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThrows(NotFoundException.class, () -> userService.findById(1));
    }

    @Test
    @Order(8)
    @DisplayName(value = "Get employee fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getEmployeeFail() throws Exception {
        String resp = mockMvc.perform(get("/api/employee/5")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(9)
    @DisplayName(value = "Get employee")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getEmployee() throws Exception {
        String resp = mockMvc.perform(get("/api/employee/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, UserDTO.class).getNameEn()).isEqualTo("admin");
    }

    @Test
    @Order(10)
    @DisplayName(value = "Get all employees")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllEmployees() throws Exception {
        String resp = mockMvc.perform(get("/api/employee/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, new TypeReference<List<UserDTO>>(){}).size()).isEqualTo(2);
    }

}