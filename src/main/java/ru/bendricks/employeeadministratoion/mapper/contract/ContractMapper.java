package ru.bendricks.employeeadministratoion.mapper.contract;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.ContractCreateDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.model.User;

@Mapper
@Component
public interface ContractMapper {

    ContractDTO toDTO(Contract contract);
    ContractDTO toDTO(ContractCreateDTO contractCreateDTO);
    ContractCreateDTO toCreateDTO(Contract contract);
    ContractCreateDTO toCreateDTO(ContractDTO contractDTO);
    Contract toModel(ContractCreateDTO contractCreateDTO);
    Contract toModel(ContractDTO contractDTO);

}
