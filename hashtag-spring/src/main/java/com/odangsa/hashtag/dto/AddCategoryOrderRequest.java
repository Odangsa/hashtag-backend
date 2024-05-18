package com.odangsa.hashtag.dto;

import com.odangsa.hashtag.domain.h2.CategoryOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCategoryOrderRequest {
    private String userId;
    private String category;

    public CategoryOrder toEntity(){
        return CategoryOrder.builder()
                .userId(userId)
                .category(category)
                .build();
    }
}
