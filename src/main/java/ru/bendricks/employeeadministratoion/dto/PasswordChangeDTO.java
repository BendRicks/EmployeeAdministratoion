package ru.bendricks.employeeadministratoion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordChangeDTO {

    private String oldPassword;
    private String newPassword;

}
