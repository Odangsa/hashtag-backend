package com.odangsa.hashtag;

import com.odangsa.hashtag.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTests {

    @Autowired
    RecommendService recommendService;

    @Test
    public void recommendServiceTest() {
        
    }
}
