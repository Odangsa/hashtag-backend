package com.odangsa.hashtag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "category_hashtag")
@Entity
public class CategoryHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_name", referencedColumnName = "hashtag_name")
    private Hashtag hashtag;

    public void setCategory(Category category){
        if(category.getCategoryHashtags().contains(this)){
            category.getCategoryHashtags().remove(this);
        }

        this.category = category;
        category.getCategoryHashtags().add(this);
    }

    public void setHashtag(Hashtag hashtag){
        this.hashtag = hashtag;
    }

}
