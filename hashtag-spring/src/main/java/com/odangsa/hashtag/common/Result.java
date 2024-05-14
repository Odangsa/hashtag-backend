package com.odangsa.hashtag.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Result {
    private int id;
    private String title;
    private List<String> hashtagList;
}
