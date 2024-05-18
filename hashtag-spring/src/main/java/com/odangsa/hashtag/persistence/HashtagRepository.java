package com.odangsa.hashtag.persistence;

import com.odangsa.hashtag.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, String> {
    Optional<Hashtag> findByHashtagName(String hashtagName);
}
