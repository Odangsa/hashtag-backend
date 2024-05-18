package com.odangsa.hashtag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.odangsa.hashtag.service.GptService;
import com.odangsa.hashtag.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class ServiceTests {

    @Autowired
    GptService gptService;

    @Test
    public void test(){
        Path fileName = Path.of("src/main/resources/static/category.txt");
        String categories = null;
        try {
            categories = Files.readString(fileName);
            categories = categories.replaceAll("\n", ", ");
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        log.info(categories);
    }

    @Test
    public void gptServiceTest() {
        try {
            List<String> keywords = new ArrayList<>();
            keywords.add("안녕");
            keywords.add("강아지");
            keywords.add("카페");
            keywords.add("바다");
            keywords.add("안경");

            List<String> categories = gptService.chat(keywords);

            for(String category : categories)
                log.info("check:"+category+"!");

            log.info("hello");
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
