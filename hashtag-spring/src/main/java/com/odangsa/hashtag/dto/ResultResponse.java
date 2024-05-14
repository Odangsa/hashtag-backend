package com.odangsa.hashtag.dto;

import com.odangsa.hashtag.common.Result;
import lombok.AllArgsConstructor;
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
