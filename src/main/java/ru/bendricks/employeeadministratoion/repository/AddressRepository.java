package ru.bendricks.employeeadministratoion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bendricks.employeeadministratoion.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
