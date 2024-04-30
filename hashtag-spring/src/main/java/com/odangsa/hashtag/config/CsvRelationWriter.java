package com.odangsa.hashtag.config;

import com.odangsa.hashtag.domain.Category;
import com.odangsa.hashtag.domain.CategoryHashtag;
import com.odangsa.hashtag.domain.Hashtag;
import com.odangsa.hashtag.dto.CsvReaderDto;
import com.odangsa.hashtag.persistence.CategoryHashtagRepository;
import com.odangsa.hashtag.persistence.CategoryRepository;
import com.odangsa.hashtag.persistence.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvRelationWriter implements ItemWriter<CsvReaderDto> {
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;
    private final CategoryHashtagRepository categoryHashtagRepository;

    @Override
    public void write(Chunk<? extends CsvReaderDto> items) throws Exception {
        List<CategoryHashtag> categoryHashtags = new ArrayList<>();

        items.forEach(getReaderDto -> {
            String categoryBundle = getReaderDto.getCategories();
            List<String> categoryNames = new ArrayList<>();
            String[] splitWords = {",", "혹은", "또는", "/", "및"};
            boolean multiple = false;
            for(String splitword : splitWords){
                if(categoryBundle.contains(splitword)) {
                    for (String token : categoryBundle.split(splitword)) {
                        categoryNames.add(token.strip());
                    }
                    multiple = true;
                }
            }
            if(!multiple){
                categoryNames.add(categoryBundle);
            }

            // categoryHashtagEntity 생성
            Hashtag hashtag = hashtagRepository.findByHashtagName(getReaderDto.getHashtag()).get();
            Category category;
            CategoryHashtag categoryHashtag;
            for(String categoryName : categoryNames){
                category = categoryRepository.findByCategoryName(categoryName).get();
                categoryHashtag = new CategoryHashtag();
                categoryHashtag.setCategory(category);
                categoryHashtag.setHashtag(hashtag);
                categoryHashtags.add(categoryHashtag);
            }
        });

        categoryHashtags.addAll(categoryHashtags);
    }
}
