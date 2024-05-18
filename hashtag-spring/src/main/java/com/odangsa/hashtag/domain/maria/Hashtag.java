package com.odangsa.hashtag.domain.maria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "hashtag")
public class Hashtag {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hashtagName")
    private String hashtagName;
    @Basic
    @Column(name = "hashtagCount")
    private long hashtagCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag that = (Hashtag) o;
        return hashtagCount == that.hashtagCount && Objects.equals(hashtagName, that.hashtagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashtagName, hashtagCount);
    }
}
