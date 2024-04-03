package com.odangsa.hashtag.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReservationResponse {
    private boolean success;
    private List<String> categories;
}
