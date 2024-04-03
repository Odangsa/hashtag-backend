package com.odangsa.hashtag;

import com.odangsa.hashtag.domain.CategoryOrder;
import com.odangsa.hashtag.domain.Customer;
import com.odangsa.hashtag.persistence.CategoryOrderRepository;
import com.odangsa.hashtag.persistence.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@Slf4j
public class RepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CategoryOrderRepository categoryOrderRepository;

    @Test
    public void saveCustomertTest(){
        //given
        Customer customer = Customer.builder().userId("andrew").build();
        customerRepository.save(customer);
        //when
        Customer retrivedCustomer = customerRepository.findByUserId(customer.getUserId()).get();
        //then
        Assertions.assertEquals(retrivedCustomer.getUserId(), "andrew");
        log.info("ID 확인 -> {}", retrivedCustomer.getId());
        log.info("UserID 확인 -> {}", retrivedCustomer.getUserId());
        log.info("TIME 확인 -> {}", retrivedCustomer.getCreatedDateTime());
    }

    @Test
    public void saveCategoryOrderTest(){
        //given
        CategoryOrder categoryOrder = CategoryOrder.builder().userId("andrew").category("box1").build();
        categoryOrderRepository.save(categoryOrder);
        CategoryOrder categoryOrder2 = CategoryOrder.builder().userId("andrew").category("box2").build();
        categoryOrderRepository.save(categoryOrder2);
        //when
        List<CategoryOrder> categoryOrders = categoryOrderRepository.findAllByUserId(categoryOrder.getUserId());
        //then
        categoryOrders.forEach(x -> {
            log.info("id -> {}",x.getId());
            log.info("userid -> {}",x.getUserId());
            log.info("category -> {}",x.getCategory());
        });
    }

    @Test
    public void deleteCategoryOrderTest(){
        //given
        CategoryOrder categoryOrder = CategoryOrder.builder().userId("andrew").category("box1").build();
        categoryOrderRepository.save(categoryOrder);
        CategoryOrder categoryOrder2 = CategoryOrder.builder().userId("andrew").category("box2").build();
        categoryOrderRepository.save(categoryOrder2);
        //when
        categoryOrderRepository.deleteAllByUserId("andrew");
        List<CategoryOrder> categoryOrders = categoryOrderRepository.findAllByUserId("andrew");
        //then
        log.info("len -> {}",categoryOrders.size());
        categoryOrders.forEach(x -> {
            log.info("id -> {}",x.getId());
            log.info("userid -> {}",x.getUserId());
            log.info("category -> {}",x.getCategory());
        });

    }
}
