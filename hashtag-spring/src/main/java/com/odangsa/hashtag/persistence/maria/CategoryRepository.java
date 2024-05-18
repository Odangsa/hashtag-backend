package com.odangsa.hashtag.persistence.maria;

import com.odangsa.hashtag.domain.maria.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByCategoryName(String categoryName);
}
