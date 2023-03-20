package ru.bendricks.employeeadministratoion.mapper;

import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.UserDTO;
import org.mapstruct.Mapper;
import ru.bendricks.employeeadministratoion.model.User;

@Component
@Mapper
public interface UserMapper {

    UserDTO toDTO(User user);
    User toModel(UserDTO userDTO);

}
