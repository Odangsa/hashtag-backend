package com.odangsa.hashtag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odangsa.hashtag.config.TranslateConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@SpringBootTest
@Import({TranslateConfig.class})
class OpenAITest {

    @Autowired
    private Map<String,String> categoryMap;

    @Test
    void test(){
        Set<String> keys = categoryMap.keySet();
        for(String key : keys){
            log.info("check : {} {}", key,categoryMap.get(key));
        }

    }
}
