package com.odangsa.hashtag.domain.maria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "super")
public class SuperEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "hashtagName")
    private String hashtagName;
    @Basic
    @Column(name = "hashtagCount")
    private long hashtagCount;
    @Basic
    @Column(name = "category")
    private String category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperEntity that = (SuperEntity) o;
        return id == that.id && hashtagCount == that.hashtagCount && Objects.equals(hashtagName, that.hashtagName) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hashtagName, hashtagCount, category);
    }
}
