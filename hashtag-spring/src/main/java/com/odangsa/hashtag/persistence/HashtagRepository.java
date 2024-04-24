package com.odangsa.hashtag.persistence;

import com.odangsa.hashtag.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
