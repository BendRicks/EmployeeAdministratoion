package ru.bendricks.employeeadministratoion.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
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
    private Integer id;

    @Column(name = "name_en", nullable = false, length = 45)
    private String nameEn;

    @Column(name = "name_ru", nullable = false, length = 45)
    private String nameRu;

    @Column(name = "surname_en", nullable = false, length = 45)
    private String surnameEn;

    @Column(name = "surname_ru", nullable = false, length = 45)
    private String surnameRu;

    @Column(name = "name_by_father_ru", length = 45)
    private String nameByFatherRu;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationTime;

    @Column(name = "passport_id", nullable = false, length = 14)
    @Size(min = 14, max = 14)
    private String passportId;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;

}
