package com.odangsa.hashtag.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class Result {
    private int id;
    private String title;
    private List<String> hashtagList;
}
