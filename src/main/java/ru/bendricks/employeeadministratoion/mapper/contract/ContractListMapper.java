package ru.bendricks.employeeadministratoion.mapper.contract;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.ContractDTO;
import ru.bendricks.employeeadministratoion.model.Contract;

import java.util.List;

@Mapper
@Component
public interface ContractListMapper {

    List<ContractDTO> toDTO(List<Contract> contracts);

}
