package ru.bendricks.employeeadministratoion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.exception.ContractNotFoundException;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.model.Contract;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.repository.ContractRepository;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Contract create(Contract contract){
        return contractRepository.save(contract);
    }

    public Contract getContractById(int id){
        return contractRepository.findById(id).orElseThrow(ContractNotFoundException::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void changeStatusById(int id, RecordStatus status){
        Contract contract = contractRepository.findById(id).orElseThrow(ContractNotFoundException::new);
        contract.setContractStatus(status);
        contractRepository.save(contract);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Contract updateAddress(Contract contract){
        return contractRepository.save(contract);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void changeEndOfWorkDateById(int id, Date date){
        Contract contract = contractRepository.findById(id).orElseThrow(ContractNotFoundException::new);
        contract.setEndOfWorkDate(date);
        contractRepository.save(contract);
    }

}
