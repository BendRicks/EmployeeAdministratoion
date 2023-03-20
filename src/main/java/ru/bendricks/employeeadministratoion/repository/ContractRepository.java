package ru.bendricks.employeeadministratoion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bendricks.employeeadministratoion.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
