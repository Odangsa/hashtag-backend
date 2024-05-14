package com.odangsa.hashtag.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TranslateConfig {
    @Bean
    public Map<String, String> CategoryMap(){
        ObjectMapper mapper = new ObjectMapper();
        Path fileName = Path.of("src/main/resources/static/keywordMapping.json");
        try {
            String json = Files.readString(fileName);
            Map<String, String> map = mapper.readValue(json, Map.class);
            log.info("check : "+map.size());
            return map;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
