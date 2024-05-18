package com.odangsa.hashtag;

import com.odangsa.hashtag.domain.h2.CategoryOrder;
import com.odangsa.hashtag.domain.h2.Customer;
import com.odangsa.hashtag.domain.maria.Category;
import com.odangsa.hashtag.domain.maria.Hashtag;
import com.odangsa.hashtag.domain.maria.SuperEntity;
import com.odangsa.hashtag.persistence.h2.CategoryOrderRepository;
import com.odangsa.hashtag.persistence.h2.CustomerRepository;
import com.odangsa.hashtag.persistence.maria.CategoryHashtagRepository;
import com.odangsa.hashtag.persistence.maria.CategoryRepository;
import com.odangsa.hashtag.persistence.maria.HashtagRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class RepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CategoryOrderRepository categoryOrderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryHashtagRepository categoryHashtagRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

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

    @Test
    public void CategoryHashtagTest(){
        List<Category> categories = categoryRepository.findAll();
        log.info("category : "+categories.get(0).getCategoryName());
        List<Hashtag> hashtags = hashtagRepository.findAll();
        log.info("hashtag : "+hashtags.get(0).getHashtagName());
        List<SuperEntity> entities = categoryHashtagRepository.findAllByCategory(categories.get(0).getCategoryName());
        int i = 1;
        for(SuperEntity entity : entities){
            log.info("[{}] : {} ",i,entity.getHashtagName());
            i++;
        }

    }
}
