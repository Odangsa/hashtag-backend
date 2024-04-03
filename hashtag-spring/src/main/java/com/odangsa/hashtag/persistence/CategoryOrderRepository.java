package com.odangsa.hashtag.persistence;

import com.odangsa.hashtag.domain.CategoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryOrderRepository extends JpaRepository<CategoryOrder, Long> {
//    Optional<CategoryOrder> findByUserId(String userId);
    List<CategoryOrder> findAllByUserId(String userId);
    @Modifying
    @Query("delete from CategoryOrder where userId = ?1")
    void deleteAllByUserId(String userId);
}
