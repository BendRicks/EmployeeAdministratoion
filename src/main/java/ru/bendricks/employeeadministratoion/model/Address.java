package ru.bendricks.employeeadministratoion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "main_schema", name = "address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Must not be empty")
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    @Column(name = "city", length = 45, nullable = false)
    private String city;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    @Column(name = "district", length = 45, nullable = false)
    private String district;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    @Column(name = "street", length = 45, nullable = false)
    private String street;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    @Column(name = "building", length = 10, nullable = false)
    private String building;

    @NotEmpty(message = "Must not be empty")
    @Size(min = 1, max = 10, message = "Length must be between 1 and 10")
    @Column(name = "room", length = 10, nullable = false)
    private String room;

    @NotNull(message = "Must not be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "address_status", length = 10, nullable = false)
    private RecordStatus addressStatus;
}
