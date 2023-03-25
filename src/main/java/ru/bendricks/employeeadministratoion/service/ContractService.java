package ru.bendricks.employeeadministratoion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.repository.ContractRepository;

@Service
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final UserService userService;

    @Autowired
    public ContractService(ContractRepository contractRepository, UserService userService) {
        this.contractRepository = contractRepository;
        this.userService = userService;
    }

    public Contract findById(int id){
        return contractRepository.findById(id).orElseThrow(() -> new NotFoundException("Contract"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Contract create(Contract contract){
        userService.findById(contract.getUser().getId()); // Throws NotFoundException if not exists
        return contractRepository.save(contract);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Contract update(Contract contract){
        return contractRepository.save(
                merge(
                        findById(contract.getId()),
                        contract
                )
        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        findById(id);
        contractRepository.deleteById(id);
    }

    private Contract merge(Contract dest, Contract src){
        if (src.getUser() != null && src.getUser().getId() != null){
            userService.findById(src.getUser().getId()); // Throws NotFoundException if not exists
            dest.setUser(src.getUser());
        }
        if (src.getEmploymentDate() != null){
            dest.setEmploymentDate(src.getEmploymentDate());
        }
        if (src.getEndOfWorkDate() != null){
            dest.setEndOfWorkDate(src.getEndOfWorkDate());
        }
        if (src.getSalary() != null){
            dest.setSalary(src.getSalary());
        }
        if (src.getSalaryIBAN() != null){
            dest.setSalaryIBAN(src.getSalaryIBAN());
        }
        if (src.getContractType() != null){
            dest.setContractType(src.getContractType());
        }
        if (src.getEmploymentType() != null){
            dest.setEmploymentType(src.getEmploymentType());
        }
        if (src.getContractStatus() != null){
            dest.setContractStatus(src.getContractStatus());
        }
        return dest;
    }
}
