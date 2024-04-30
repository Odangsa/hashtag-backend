package com.odangsa.hashtag.config;

import com.odangsa.hashtag.domain.Category;
import com.odangsa.hashtag.domain.CategoryHashtag;
import com.odangsa.hashtag.domain.Hashtag;
import com.odangsa.hashtag.dto.CsvReaderDto;
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
public class CsvDataWriter implements ItemWriter<CsvReaderDto> {

    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public void write(Chunk<? extends CsvReaderDto> items) throws Exception {
        List<Category> categories = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        items.forEach(getReaderDto -> {
            // hashtagEntity 생성
            Hashtag hashtag = new Hashtag();
            hashtag.setHashtagName(getReaderDto.getHashtag());
            hashtag.setCount(Integer.parseInt(getReaderDto.getCount()));
            hashtags.add(hashtag);

            // categoryEntity 생성
            String categoryBundle = getReaderDto.getCategories();
            Category category;
            String[] splitWords = {",", "혹은", "또는", "/", "및"};
            boolean multiple = false;
            for(String splitword : splitWords){
                if(categoryBundle.contains(splitword)) {
                    for (String token : categoryBundle.split(splitword)) {
                        category = new Category();
                        category.setCategoryName(token.strip());
                        categories.add(category);
                    }
                    multiple = true;
                }
            }
            if(!multiple){
                category = new Category();
                category.setCategoryName(categoryBundle);
                categories.add(category);
            }
        });

        hashtagRepository.saveAll(hashtags);
        for(Category category : categories){
            if(categoryRepository.findByCategoryName(category.getCategoryName()).isEmpty())
                categoryRepository.save(category);
        }
    }
}
