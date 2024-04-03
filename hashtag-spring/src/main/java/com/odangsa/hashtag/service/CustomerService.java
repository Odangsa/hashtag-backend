package com.odangsa.hashtag.service;

import com.odangsa.hashtag.domain.Customer;
import com.odangsa.hashtag.persistence.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer save(String userId){
        return customerRepository.save(Customer.builder().userId(userId).build());
    }

    public Customer findByUserId(String userId){
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+userId));
    }

    public void delete(Customer customer){
        customerRepository.delete(customer);
    }
}
