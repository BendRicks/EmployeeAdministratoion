package ru.bendricks.employeeadministratoion.mapper.contract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.ContractCreateDTO;
import ru.bendricks.employeeadministratoion.model.Contract;

@Mapper
@Component
public interface ContractMapper {

    @Mapping(target = "user", ignore = true)
    ContractDTO toDTO(Contract contract);
    Contract toModel(ContractCreateDTO contractCreateDTO);
    Contract toModel(ContractDTO contractDTO);

}
