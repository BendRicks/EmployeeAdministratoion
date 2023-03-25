package ru.bendricks.employeeadministratoion.mapper.user;

import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.entity.UserDTO;
import ru.bendricks.employeeadministratoion.dto.entity.create.UserCreateDTO;
import org.mapstruct.Mapper;
import ru.bendricks.employeeadministratoion.mapper.address.AddressListMapper;
import ru.bendricks.employeeadministratoion.mapper.contract.ContractListMapper;
import ru.bendricks.employeeadministratoion.model.User;

@Mapper(uses = {ContractListMapper.class, AddressListMapper.class})
@Component
public interface UserMapper {

    UserDTO toDTO(User user);
    UserDTO toDTO(UserCreateDTO userCreateDTO);
    UserCreateDTO toCreateDTO(User user);
    UserCreateDTO toCreateDTO(UserDTO userDTO);
    User toModel(UserCreateDTO userCreateDTO);
    User toModel(UserDTO userDTO);

}

