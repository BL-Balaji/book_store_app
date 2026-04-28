package com.bookstore.customer.repository;

import com.bookstore.customer.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    List<CustomerAddress> findByUserId(String userId);
    Optional<CustomerAddress> findByUserIdAndIsDefaultTrue(String userId);
}
