package com.odangsa.hashtag.service;

import com.odangsa.hashtag.domain.h2.CategoryOrder;
import com.odangsa.hashtag.domain.h2.Customer;
import com.odangsa.hashtag.dto.AddCategoryOrderRequest;
import com.odangsa.hashtag.dto.ReservationRequest;
import com.odangsa.hashtag.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final CustomerService customerService;
    private final CategoryOrderService categoryOrderService;

    @Transactional("secondTransactionManager")
    public boolean registerResult(ReservationRequest request){
        try {
            customerService.findByUserId(request.getUserId());
            return false;
        } catch(IllegalArgumentException e){
            customerService.save(request.getUserId());
            for(String category: request.getCategories())
                categoryOrderService.save(new AddCategoryOrderRequest(request.getUserId(),category));
            return true;
        }
    }

    public ReservationResponse getResult(String userId){
        ReservationResponse response = new ReservationResponse();
        Customer customer;
        // Check userId : available?
        try {
            customer = customerService.findByUserId(userId);
            response.setSuccess(true);
        } catch (IllegalArgumentException e){
            response.setSuccess(false);
            return response;
        }

        // Get Categories
        List<CategoryOrder> orders = categoryOrderService.findAllByUserId(userId);
        if(orders.size() <= 0){
            response.setSuccess(false);
            return response;
        }
        List<String> categories = orders.stream().map(e->e.getCategory()).collect(Collectors.toList());
        response.setCategories(categories);

        // Delete Finished Request
        categoryOrderService.deleteAllByUserId(userId);
        customerService.delete(customer);

        return response;
    }
}
