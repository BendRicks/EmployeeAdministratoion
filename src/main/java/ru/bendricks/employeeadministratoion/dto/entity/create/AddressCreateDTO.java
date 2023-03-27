package ru.bendricks.employeeadministratoion.dto.entity.create;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.model.RecordStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDTO {

    @NotNull(message = "Must not be empty")
    private UserDTO user;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String city;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String district;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String street;

    @NotBlank(message = "Must not be empty")
    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    private String building;

    @NotBlank(message = "Must not be empty")
    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    private String room;

    @NotNull(message = "Must not be empty")
    private RecordStatus addressStatus;

}
