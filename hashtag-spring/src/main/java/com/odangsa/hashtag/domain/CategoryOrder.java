package com.odangsa.hashtag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "categoryOrder")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String category;

    @Builder
    public CategoryOrder(String userId, String category){
        this.userId = userId;
        this.category = category;
    }
}
