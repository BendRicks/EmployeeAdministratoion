package ru.bendricks.employeeadministratoion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.exception.AddressNotFoundException;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.model.RecordStatus;
import ru.bendricks.employeeadministratoion.repository.AddressRepository;

@Service
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Address create(Address address){
        return addressRepository.save(address);
    }

    public Address getContractById(int id){
        return addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void changeStatusById(int id, RecordStatus status){
        Address address = addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
        address.setAddressStatus(status);
        addressRepository.save(address);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Address updateAddress(Address address){
        return addressRepository.save(address);
    }

}
