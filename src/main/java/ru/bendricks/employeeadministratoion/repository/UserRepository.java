package ru.bendricks.employeeadministratoion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bendricks.employeeadministratoion.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
