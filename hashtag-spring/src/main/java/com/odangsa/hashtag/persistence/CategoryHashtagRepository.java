package com.odangsa.hashtag.persistence;

import com.odangsa.hashtag.domain.CategoryHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHashtagRepository extends JpaRepository<CategoryHashtag, Long> {
}
