package com.odangsa.hashtag.service;

import com.odangsa.hashtag.domain.h2.Customer;
import com.odangsa.hashtag.persistence.h2.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional("secondTransactionManager")
    public Customer save(String userId){
        return customerRepository.save(Customer.builder().userId(userId).build());
    }

    public Customer findByUserId(String userId){
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+userId));
    }

    @Transactional("secondTransactionManager")
    public void delete(Customer customer){
        customerRepository.delete(customer);
    }
}
