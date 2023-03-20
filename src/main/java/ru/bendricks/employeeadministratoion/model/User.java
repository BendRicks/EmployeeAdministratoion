package ru.bendricks.employeeadministratoion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "main_schema", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_ru", nullable = false)
    private String nameRu;

    @Column(name = "surname_en", nullable = false)
    private String surnameEn;

    @Column(name = "surname_ru", nullable = false)
    private String surnameRu;

    @Column(name = "name_by_father_ru")
    private String nameByFatherRu;

    @Column(name = "email")
    private String email;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationTime;

    @Column(name = "passport_id", nullable = false)
    private String passportId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;

}
