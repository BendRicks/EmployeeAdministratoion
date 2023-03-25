package ru.bendricks.employeeadministratoion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.employeeadministratoion.exception.NotFoundException;
import ru.bendricks.employeeadministratoion.model.Address;
import ru.bendricks.employeeadministratoion.repository.AddressRepository;

@Service
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public Address findById(int id){
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Address create(Address address){
        userService.findById(address.getUser().getId()); // Throws NotFoundException if not exists
        return addressRepository.save(address);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Address update(Address address){
        return addressRepository.save(
                merge(
                        findById(address.getId()),
                        address
                )
        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        findById(id);
        addressRepository.deleteById(id);
    }

    private Address merge(Address dest, Address src){
        if (src.getUser() != null && src.getUser().getId() != null){
            userService.findById(src.getUser().getId()); // Throws NotFoundException if not exists
            dest.setUser(src.getUser());
        }
        if (src.getCity() != null){
            dest.setCity(src.getCity());
        }
        if (src.getDistrict() != null){
            dest.setDistrict(src.getDistrict());
        }
        if (src.getStreet() != null){
            dest.setStreet(src.getStreet());
        }
        if (src.getBuilding() != null){
            dest.setBuilding(src.getBuilding());
        }
        if (src.getRoom() != null){
            dest.setRoom(src.getRoom());
        }
        if (src.getAddressStatus() != null){
            dest.setAddressStatus(src.getAddressStatus());
        }
        return dest;
    }

}
