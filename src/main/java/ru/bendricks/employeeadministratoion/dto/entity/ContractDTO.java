package ru.bendricks.employeeadministratoion.dto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ru.bendricks.employeeadministratoion.model.ContractType;
import ru.bendricks.employeeadministratoion.model.EmploymentType;
import ru.bendricks.employeeadministratoion.model.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    @NotNull(message = "Must not be empty")
    private Integer id;

    private UserDTO user;

    private LocalDateTime employmentDate;

    private LocalDateTime endOfWorkDate;

    @Positive(message = "Must be positive")
    private BigDecimal salary;

    @Size(min = 28, max = 28)
    private String salaryIBAN;

    private ContractType contractType;

    private EmploymentType employmentType;

    private RecordStatus contractStatus;

}
