package com.odangsa.hashtag.controller;

import com.odangsa.hashtag.dto.ReservationRequest;
import com.odangsa.hashtag.dto.ReservationResponse;
import com.odangsa.hashtag.dto.ResultResponse;
import com.odangsa.hashtag.service.ReservationService;
import com.odangsa.hashtag.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class HashtagController {

    private final ReservationService reservationService;

    @PostMapping("/{userId}/hashtag")
    public ResponseEntity<?> requestHashtags(
            @PathVariable("userId") String userId,
            @RequestParam MultipartFile picture,
            @RequestParam String place){

        // Instead of AI
        List<String> categories = new ArrayList<>();
        String filename ="";
        if(!picture.isEmpty())
            filename = picture.getOriginalFilename();

        categories.add(filename);
        for(String word : place.split(" "))
            categories.add(word);

        // Reservation
        ReservationRequest request = new ReservationRequest(userId, categories);
        boolean success = reservationService.registerResult(request);
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", success);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{userId}/hashtag")
    public ResponseEntity<?> requestHashtags(@PathVariable("userId") String userId){
        // Solve Reservation
        ReservationResponse reservationResponse = reservationService.getResult(userId);
        if(!reservationResponse.isSuccess())
            return ResponseEntity.badRequest().body(null);

        // category 넣어주기
        List<String> dummy = new ArrayList<String>(List.of("#hello", "#hi", "#Guten Morgen"));
        AtomicInteger index = new AtomicInteger();
        List<Result> results = reservationResponse.getCategories().stream().map(e->
            Result.builder().id(index.getAndIncrement()).title(e).hashtagList(dummy).build()).collect(Collectors.toList());
        ResultResponse resultResponse = new ResultResponse(reservationResponse.isSuccess(), results);

        return ResponseEntity.ok().body(resultResponse);
    }
}
