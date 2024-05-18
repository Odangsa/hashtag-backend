package com.odangsa.hashtag.persistence.h2;

import com.odangsa.hashtag.domain.h2.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserId(String userId);
}
