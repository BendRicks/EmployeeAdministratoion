package ru.bendricks.employeeadministratoion.mapper.address;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.AddressDTO;
import ru.bendricks.employeeadministratoion.model.Address;

import java.util.List;

@Mapper(uses = AddressMapper.class)
@Component
public interface AddressListMapper {

    List<AddressDTO> toDTO(List<Address> addresses);

}
