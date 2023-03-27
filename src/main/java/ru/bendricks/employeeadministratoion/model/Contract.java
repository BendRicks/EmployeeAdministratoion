package ru.bendricks.employeeadministratoion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "main_schema", name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "employment_date", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime employmentDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_of_work_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime endOfWorkDate;

    @NotNull
    @Column(name = "salary", precision = 7, scale = 2, nullable = false)
    private BigDecimal salary;

    @NotEmpty
    @Size(min = 28, max = 28)
    @Column(name = "salary_iban", length = 28, nullable = false)
    private String salaryIBAN;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", length = 10, nullable = false)
    private ContractType contractType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", length = 10, nullable = false)
    private EmploymentType employmentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status", length = 10, nullable = false)
    private RecordStatus contractStatus;
}
