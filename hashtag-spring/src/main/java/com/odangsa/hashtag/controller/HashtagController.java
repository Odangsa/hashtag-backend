package com.odangsa.hashtag.controller;

import com.odangsa.hashtag.dto.ResponseDTO;
import com.odangsa.hashtag.dto.SectionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("hashtag")
public class HashtagController {
    @PostMapping
    public ResponseEntity<?> recommendHashtag(
            @RequestParam MultipartFile file, @RequestParam String place){
        String filename = "";
        if(!file.isEmpty()){
            filename = file.getOriginalFilename();
        }

        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for(int i=0; i<10; i++){
            list.add(filename);
        }
        SectionDTO a = SectionDTO.builder().title(filename).hashtags(list).build();

        for(int i=0; i<10; i++){
            list2.add(place);
        }
        SectionDTO b = SectionDTO.builder().title(place).hashtags(list2).build();
        List<SectionDTO> dtos = new ArrayList<>();
        dtos.add(a);
        dtos.add(b);

        ResponseDTO<SectionDTO> response = ResponseDTO.<SectionDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
}
