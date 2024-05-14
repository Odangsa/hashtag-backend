package com.odangsa.hashtag.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record ChatGptRequest(String model, List<Message> messages,
                             double temperature, int max_tokens) {

    public static ChatGptRequest create(List<String> keywords){
        Path fileName = Path.of("src/main/resources/static/category.txt");
        String categories = null;
        try {
            categories = Files.readString(fileName);
            categories = categories.replaceAll("\n", ", ");
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        return new ChatGptRequest(
                "gpt-3.5-turbo",
                Arrays.asList(
                        new Message(
                                "system",
                                String.format("당신은 해시태그를 다음 카테고리로 분류하는 도우미입니다: %s. 각 해시태그에 대해 주어진 카테고리 중 가장 적합한 것을 선택해주세요. 만약 주어진 카테고리 중 어느 것도 적합하지 않다면 '기타'를 선택해주세요.",categories)
                        ),
                        new Message(
                                "user",
                                "다음 해시태그들을 분류해주세요. 각 해시태그는 줄바꿈으로 구분되어 있습니다. 분류 결과는 '해시태그 : 카테고리' 형식으로 해시태그와 카테고리를 콜론(:)으로 구분하여 줄바꿈으로 구분된 목록으로 제공해주세요.\n\n"+String.join("\n",keywords))
                ),
                0.7,
                300);
    }
}

class Message{
    @JsonProperty
    String role;
    @JsonProperty
    String content;

    Message(String role, String content){
        this.role = role;
        this.content = content;
    }
}