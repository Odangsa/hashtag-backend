package com.odangsa.hashtag.service;

import com.odangsa.hashtag.domain.CategoryOrder;
import com.odangsa.hashtag.dto.AddCategoryOrderRequest;
import com.odangsa.hashtag.persistence.CategoryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryOrderService {

    private final CategoryOrderRepository categoryOrderRepository;

    public CategoryOrder save(AddCategoryOrderRequest request){
        return categoryOrderRepository.save(request.toEntity());
    }

    public List<CategoryOrder> findAllByUserId(String userId){
        return categoryOrderRepository.findAllByUserId(userId);
    }

    public void deleteAllByUserId(String userId){
        categoryOrderRepository.deleteAllByUserId(userId);
    }
}
