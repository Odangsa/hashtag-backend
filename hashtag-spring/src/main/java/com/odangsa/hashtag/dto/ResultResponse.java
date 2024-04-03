package com.odangsa.hashtag.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odangsa.hashtag.utils.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResultResponse {
    private boolean success;
    private List<Result> results;
}
