package ru.bendricks.employeeadministratoion.dto.entity.create;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.model.ContractType;
import ru.bendricks.employeeadministratoion.model.EmploymentType;
import ru.bendricks.employeeadministratoion.model.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreateDTO {

    @NotNull(message = "Must not be empty")
    private UserDTO user;

    @NotNull(message = "Must not be empty")
    private LocalDateTime employmentDate;

    @NotNull(message = "Must not be empty")
    private LocalDateTime endOfWorkDate;

    @Positive(message = "Must be positive")
    @NotNull(message = "Must not be empty")
    private BigDecimal salary;

    @NotBlank(message = "Must not be empty")
    @Size(min = 28, max = 28, message = "Length must be 28")
    private String salaryIBAN;

    @NotNull(message = "Must not be empty")
    private ContractType contractType;

    @NotNull(message = "Must not be empty")
    private EmploymentType employmentType;

    @NotNull(message = "Must not be empty")
    private RecordStatus contractStatus;

}
