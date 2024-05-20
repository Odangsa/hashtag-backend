package com.odangsa.hashtag.persistence.h2;

import com.odangsa.hashtag.domain.h2.CategoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryOrderRepository extends JpaRepository<CategoryOrder, Long> {
//    Optional<CategoryOrder> findByUserId(String userId);
    List<CategoryOrder> findAllByUserId(String userId);
    @Modifying
    @Query("delete from CategoryOrder where userId = ?1")
    void deleteAllByUserId(String userId);
}
