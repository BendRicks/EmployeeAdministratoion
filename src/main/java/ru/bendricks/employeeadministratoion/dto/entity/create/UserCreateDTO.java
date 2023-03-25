package ru.bendricks.employeeadministratoion.dto.entity.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    public String nameEn;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String nameRu;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String surnameEn;

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String surnameRu;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String nameByFatherRu;

    @NotBlank(message = "Must not be empty")
    @Email(message = "Not an email")
    @Size(min = 7, max = 100, message = "Length must be between 7 and 100")
    private String email;

    @NotBlank(message = "Must not be empty")
    @Size(min = 14, max = 14, message = "Length must be 14")
    private String passportId;

}
