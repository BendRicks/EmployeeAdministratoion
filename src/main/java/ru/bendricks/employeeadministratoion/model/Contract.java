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
import java.util.Date;

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
    private int id;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "employment_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date employmentDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "end_of_work_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endOfWorkDate;

    @NotNull
    @Column(name = "salary")
    private BigDecimal salary;

    @NotEmpty
    @Size(min = 28, max = 28)
    @Column(name = "salary_iban")
    private String salaryIBAN;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status")
    private RecordStatus contractStatus;
}
