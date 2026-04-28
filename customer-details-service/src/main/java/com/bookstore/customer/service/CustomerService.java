package com.bookstore.customer.service;

import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.customer.entity.CustomerAddress;
import com.bookstore.customer.repository.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class CustomerService {

    private final CustomerAddressRepository addressRepository;

    @Transactional
    public CustomerAddress addAddress(CustomerAddress address) {
        if (address.getIsDefault()) {
            addressRepository.findByUserIdAndIsDefaultTrue(address.getUserId())
                    .ifPresent(a -> { a.setIsDefault(false); addressRepository.save(a); });
        }
        return addressRepository.save(address);
    }

    public List<CustomerAddress> getAddresses(String userId) {
        return addressRepository.findByUserId(userId);
    }

    public CustomerAddress getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));
    }

    @Transactional
    public CustomerAddress updateAddress(Long id, CustomerAddress req) {
        CustomerAddress address = getAddressById(id);
        if (req.getFullName() != null) address.setFullName(req.getFullName());
        if (req.getPhoneNumber() != null) address.setPhoneNumber(req.getPhoneNumber());
        if (req.getAddressLine1() != null) address.setAddressLine1(req.getAddressLine1());
        if (req.getAddressLine2() != null) address.setAddressLine2(req.getAddressLine2());
        if (req.getCity() != null) address.setCity(req.getCity());
        if (req.getState() != null) address.setState(req.getState());
        if (req.getZipCode() != null) address.setZipCode(req.getZipCode());
        if (req.getCountry() != null) address.setCountry(req.getCountry());
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
