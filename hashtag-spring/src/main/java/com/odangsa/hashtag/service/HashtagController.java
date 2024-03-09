package com.odangsa.hashtag.service;

import com.odangsa.hashtag.dto.ResponseDTO;
import com.odangsa.hashtag.dto.SectionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("hashtag")
public class HashtagController {
    @PostMapping
    public ResponseEntity<?> recommendHashtag(
            @RequestParam MultipartFile file, @RequestParam String place){
        String filename = "";
        if(!file.isEmpty()){
            String filename = file.getOriginalFilename();
        }

        SectionDTO a = SectionDTO.builder().title(filename).hashtags().build();
        SectionDTO b =
        List<SectionDTO> dtos = null;
        ResponseDTO<SectionDTO> response = ResponseDTO.<SectionDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }
}
