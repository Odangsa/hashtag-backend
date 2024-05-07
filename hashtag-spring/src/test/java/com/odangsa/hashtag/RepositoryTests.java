package com.odangsa.hashtag;

import com.odangsa.hashtag.domain.*;
import com.odangsa.hashtag.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
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
    @Transactional
    public void CategoryHashtagTest(){
        Category category = new Category();
        category.setCategoryName("초밥");
        categoryRepository.save(category);

        Hashtag hashtag = new Hashtag();
        hashtag.setHashtagName("새우초밥");
        hashtag.setCount(1000);
        hashtagRepository.save(hashtag);

        Hashtag hashtag2 = new Hashtag();
        hashtag2.setHashtagName("광어초밥");
        hashtag2.setCount(2000);
        hashtagRepository.save(hashtag2);

        CategoryHashtag categoryHashtag = new CategoryHashtag();
        categoryHashtag.setCategory(category);
        categoryHashtag.setHashtag(hashtag);
        categoryHashtagRepository.save(categoryHashtag);

        CategoryHashtag categoryHashtag2 = new CategoryHashtag();
        categoryHashtag2.setCategory(category);
        categoryHashtag2.setHashtag(hashtag2);
        categoryHashtagRepository.save(categoryHashtag2);

        log.info("saved Category : " + categoryRepository.findByCategoryName("초밥").get().getCategoryName());
        for(CategoryHashtag ch : categoryRepository.findByCategoryName("초밥").get().getCategoryHashtags())
            log.info("saved Hashtag : " + ch.getHashtag().getHashtagName());
    }
}
