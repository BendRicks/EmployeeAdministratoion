package ru.bendricks.employeeadministratoion.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.dto.UserDTO;
import ru.bendricks.employeeadministratoion.model.User;

import java.util.List;

@Component
@Mapper(uses = UserMapper.class)
public interface UserListMapper {

    List<UserDTO> toDTO(List<User> users);
    List<User> toModel(List<UserDTO> usersDTOs);

}
