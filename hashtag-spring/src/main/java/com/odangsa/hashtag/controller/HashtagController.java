package com.odangsa.hashtag.controller;

import com.odangsa.hashtag.dto.ReservationRequest;
import com.odangsa.hashtag.dto.ReservationResponse;
import com.odangsa.hashtag.dto.ResultResponse;
import com.odangsa.hashtag.persistence.maria.CategoryHashtagRepository;
import com.odangsa.hashtag.service.RecommendService;
import com.odangsa.hashtag.service.ReservationService;
import com.odangsa.hashtag.common.Result;
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
    private final RecommendService recommendService;
    private final CategoryHashtagRepository categoryHashtagRepository;

    @PostMapping("/{userId}/hashtag")
    public ResponseEntity<?> requestHashtags(
            @PathVariable("userId") String userId,
            @RequestParam MultipartFile picture,
            @RequestParam String place){

        List<String> categories = recommendService.recommendCategory(picture, place);

        // Reservation
        if(categories == null)
            return ResponseEntity.internalServerError().body(new HashMap<String,Boolean>().put("success", false));
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
            Result.builder()
                    .id(index.getAndIncrement())
                    .title(e)
                    .hashtagList(categoryHashtagRepository.findAllByCategory(e).stream().map(h->h.getHashtagName()).collect(Collectors.toList()))
                    .build()).collect(Collectors.toList());
        ResultResponse resultResponse = new ResultResponse(reservationResponse.isSuccess(), results);

        return ResponseEntity.ok().body(resultResponse);
    }
}
