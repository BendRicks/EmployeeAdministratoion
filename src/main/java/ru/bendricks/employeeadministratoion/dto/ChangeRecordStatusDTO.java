package ru.bendricks.employeeadministratoion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.employeeadministratoion.model.RecordStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRecordStatusDTO {

    private int id;
    private RecordStatus status;

}
