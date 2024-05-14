package com.odangsa.hashtag.config;


import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class ChatGPTConfig {

//    @Value("${gpt.token}")
//    private String token;
//
//    @Bean
//    public OpenAiService openAiService(){
//        log.info("token : {}을 활용한 OpenAiService을 생성합니다.", token);
//        return new OpenAiService(token, Duration.ofSeconds(60));
//    }
}
