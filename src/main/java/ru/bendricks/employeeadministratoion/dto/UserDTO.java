package ru.bendricks.employeeadministratoion.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.model.Contract;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    public int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, message = "Name size must be greater than 1")
    public String nameEn;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, message = "Name size must be greater than 1")
    private String nameRu;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 1, message = "Surname size must be greater than 1")
    private String surnameEn;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 1, message = "Surname size must be greater than 1")
    private String surnameRu;

    @Size(min = 1, message = "Name by father size must be greater than 1")
    private String nameByFatherRu;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Not an email")
    private String email;

    @NotEmpty
    @Size(min = 14, max = 14)
    private String passportId;

    private List<Address> addresses;
    private List<Contract> contracts;

}
