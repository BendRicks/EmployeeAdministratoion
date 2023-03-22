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
    private int id;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private User user;

    @NotEmpty
    @Size(min = 2)
    @Column(name = "city")
    private String city;

    @NotEmpty
    @Size(min = 2)
    @Column(name = "district")
    private String district;

    @NotEmpty
    @Size(min = 2)
    @Column(name = "street")
    private String street;

    @NotEmpty
    @Size(min = 2)
    @Column(name = "building")
    private String building;

    @NotEmpty
    @Size(min = 2)
    @Column(name = "room")
    private String room;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "address_status")
    private RecordStatus addressStatus;
}
