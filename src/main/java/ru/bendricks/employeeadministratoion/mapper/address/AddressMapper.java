package ru.bendricks.employeeadministratoion.mapper.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.AddressDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.AddressCreateDTO;
import ru.bendricks.employeeadministratoion.model.Address;

@Mapper
@Component
public interface AddressMapper {

    @Mapping(target = "user", ignore = true)
    AddressDTO toDTO(Address address);
    Address toModel(AddressDTO addressDTO);
    Address toModel(AddressCreateDTO addressCreateDTO);

}
