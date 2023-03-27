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
import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.AddressCreateDTO;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.service.AddressService;

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
class AddressControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AddressService addressService;

    @Autowired
    public AddressControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AddressService addressService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.addressService = addressService;
    }

    @Test
    @Order(1)
    void loading(){
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(addressService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName(value = "Add address fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddAddressFail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(5);
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO(userDTO, "M", "L", "K", "", "", null);
        System.out.println(objectMapper.writeValueAsString(addressCreateDTO));
        String resp = mockMvc.perform(post("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(3)
    @DisplayName(value = "Add address")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testAddAddress() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO(userDTO, "Minsk", "Leninsky", "Kirova", "19", "35", RecordStatus.OPERATING);
        System.out.println(objectMapper.writeValueAsString(addressCreateDTO));
        String resp = mockMvc.perform(post("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressCreateDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, AddressDTO.class).getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName(value = "Change address fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testChangeAddressFail() throws Exception {
        AddressDTO addressDTO = new AddressDTO(1, null, null, null, null, "", null, null);
        String resp = mockMvc.perform(put("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not created");
    }

    @Test
    @Order(5)
    @DisplayName(value = "Change address")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testChangeAddress() throws Exception {
        AddressDTO addressDTO = new AddressDTO(1, null, null, null, null, "30", null, null);
        String resp = mockMvc.perform(put("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(addressService.findById(1).getBuilding()).isEqualTo("30");
    }

    @Test
    @Order(6)
    @DisplayName(value = "Delete address fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteContractByIdFail() throws Exception {
        AddressDTO addressDTO = new AddressDTO(5, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(7)
    @DisplayName(value = "Delete address")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteContractById() throws Exception {
        AddressDTO addressDTO = new AddressDTO(1, null, null, null, null, null, null, null);
        String resp = mockMvc.perform(delete("/api/address/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addressDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThrows(NotFoundException.class, () -> addressService.findById(1));
    }

    @Test
    @Order(8)
    @DisplayName(value = "Get address fail")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetAddressByIdFail() throws Exception {
        String resp = mockMvc.perform(get("/api/address/6")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("not found");
    }

    @Test
    @Order(9)
    @DisplayName(value = "Get address")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/create_addresses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetAddressById() throws Exception {
        String resp = mockMvc.perform(get("/api/address/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, AddressDTO.class).getBuilding()).isEqualTo("19");
    }

}