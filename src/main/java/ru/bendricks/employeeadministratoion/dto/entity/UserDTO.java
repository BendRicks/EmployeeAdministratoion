package ru.bendricks.employeeadministratoion.dto.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.bendricks.employeeadministratoion.model.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Must not be empty")
    private Integer id;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String nameEn;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String nameRu;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String surnameEn;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String surnameRu;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String nameByFatherRu;

    @Email(message = "Not an email")
    @Size(min = 7, max = 100, message = "Length must be between 7 and 100")
    private String email;

    private LocalDateTime creationTime;

    @Size(min = 14, max = 14, message = "Length must be 14")
    private String passportId;

    private UserRole role;

    private List<AddressDTO> addresses;

    private List<ContractDTO> contracts;

}
