package ru.bendricks.employeeadministratoion.dto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.bendricks.employeeadministratoion.model.RecordStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotNull(message = "Must not be empty")
    private Integer id;

    private UserDTO user;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String city;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String district;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String street;

    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    private String building;

    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    private String room;

    private RecordStatus addressStatus;

}
