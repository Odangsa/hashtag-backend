package com.odangsa.hashtag.service;

import com.clarifai.grpc.auth.scope.S;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import com.odangsa.hashtag.common.ChatGptRequest;
import com.odangsa.hashtag.common.ChatGptResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class GptService {

    String apiUrl = "https://api.openai.com/v1/chat/completions";
    String openaiApiKey = "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";
    ObjectMapper objectMapper;
    HttpClient httpClient;

    @PostConstruct
    public void initialize() throws Exception{
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        httpClient = HttpClient.newBuilder().build();
    }

    public List<String> chat(List<String> keywords) throws Exception{
        ChatGptRequest chatGptRequest = ChatGptRequest.create(keywords);
        String requestBody = objectMapper.writeValueAsString(chatGptRequest);
        String responseBody = sendRequest(requestBody);
        log.info("debug : "+ responseBody);
        ChatGptResponse chatGptResponse = objectMapper.readValue(responseBody, ChatGptResponse.class);

        String result = chatGptResponse.getText().orElseThrow();
        String[] strings = result.split("\n");
        List<String> categories = new ArrayList<>();
        for(String string : strings)
            categories.add(string.split(":")[1].strip());

        return List.copyOf(Set.copyOf(categories));
    }

    private String sendRequest(String requestBodyAsJson) throws Exception{
        URI url = URI.create(apiUrl);
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+openaiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyAsJson)).build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
