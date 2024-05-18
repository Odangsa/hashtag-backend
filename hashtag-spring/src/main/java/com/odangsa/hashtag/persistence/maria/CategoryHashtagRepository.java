package com.odangsa.hashtag.persistence.maria;

import com.odangsa.hashtag.domain.maria.SuperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryHashtagRepository extends JpaRepository<SuperEntity, Long> {
    List<SuperEntity> findAllByCategory(String category);
}