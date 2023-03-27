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
import ru.bendricks.employeeadministratoion.dto.AuthenticationRequestDTO;
import ru.bendricks.employeeadministratoion.dto.AuthenticationResponse;
import ru.bendricks.employeeadministratoion.dto.MessageResponse;
import ru.bendricks.employeeadministratoion.dto.PasswordChangeDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import ru.bendricks.employeeadministratoion.security.util.JWTUtil;
import ru.bendricks.employeeadministratoion.service.UserService;
import ru.bendricks.employeeadministratoion.util.constants.GeneralConstants;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {EmployeeAdministrationApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, JWTUtil jwtUtil) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    @Test
    @Order(1)
    void loading(){
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(jwtUtil).isNotNull();
    }

    @Test
    @Order(2)
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName(value = "Perform login fail")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void performLoginFail() throws Exception {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO("admin@admin.com", "password");
        String resp = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertThat(objectMapper.readValue(resp, MessageResponse.class).getMessage()).contains("Bad credentials");
    }

    @Test
    @Order(3)
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName(value = "Perform login")
    @Sql(value = "/sql/create_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/clear_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void performLogin() throws Exception {
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO("admin@admin.com", "admin");
        String resp = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authenticationRequestDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(jwtUtil.validateTokenAndRetrieveClaim(objectMapper.readValue(resp, AuthenticationResponse.class).getToken())).isEqualTo(1);
    }

}